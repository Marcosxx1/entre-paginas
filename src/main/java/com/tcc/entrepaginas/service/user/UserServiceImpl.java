package com.tcc.entrepaginas.service.user;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.dto.UpdateUserNameLoginAndEmailRequest;
import com.tcc.entrepaginas.domain.dto.UserListResponse;
import com.tcc.entrepaginas.domain.entity.*;
import com.tcc.entrepaginas.domain.registration.RegistrationCompleteEvent;
import com.tcc.entrepaginas.domain.registration.VerificationToken;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.mapper.user.UserMapper;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import com.tcc.entrepaginas.service.community.CommunityServiceNew;
import com.tcc.entrepaginas.service.enums.EnumListingService;
import com.tcc.entrepaginas.service.verificationtoken.VerificationTokenService;
import com.tcc.entrepaginas.utils.imageupload.ImageUtils;
import com.tcc.entrepaginas.utils.registration.UrlUtils;
import com.tcc.entrepaginas.utils.user.RegistroDeUsuario;
import com.tcc.entrepaginas.utils.user.UserUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RegistroDeUsuario registroDeUsuario;
    private final UserUtils userUtils;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenService tokenService;

    private static final Path ROOT = Paths.get("uploads");

    @PostConstruct
    public void init() {
        ImageUtils.init(ROOT);
    }

    @Override
    public String registerAndRedirect(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";
        }
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("novoUsuarioRequest", new NovoUsuarioRequest());
        return "CadastrarUsuario";
    }

    @Override
    public String saveUserFromForm(
            NovoUsuarioRequest novoUsuarioRequest,
            BindingResult result,
            RedirectAttributes attributes,
            Model model,
            HttpServletRequest request) {
        registroDeUsuario.validarUsuario(novoUsuarioRequest, result, request);

        if (result.hasErrors()) {
            model.addAttribute("novoUsuarioRequest", novoUsuarioRequest);
            return "CadastrarUsuario";
        }

        Usuario usuario = userMapper.toUsuario(novoUsuarioRequest, passwordEncoder);
        usuarioRepository.save(usuario);

        publisher.publishEvent(new RegistrationCompleteEvent(usuario, UrlUtils.getApplicationUrl(request)));

        attributes.addFlashAttribute("mensagem", "Cadastro efetuado com sucesso!");
        return "redirect:/user/register?success";
    }

    @Override
    public String saveUserImage(Authentication authentication, MultipartFile image, HttpServletRequest request) {
        Usuario usuario = userUtils.getUsuarioObjectFromAuthentication(authentication);

        String fileName = ImageUtils.saveImageWithUniqueName(image, ROOT);

        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        String url = baseUrl + "/uploads/" + fileName;

        usuario.setImagem(url);

        usuarioRepository.save(usuario);

        return "redirect:/infos";
    }

    @Override
    public String updateUserNameLoginAndEmail(
            Usuario user,
            String id,
            UpdateUserNameLoginAndEmailRequest updateUserNameLoginAndEmailRequest,
            BindingResult result,
            RedirectAttributes attributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("updateUserNameLoginAndEmailRequest", updateUserNameLoginAndEmailRequest);
            model.addAttribute("user", user);

            return "forward:/infos";
        }

        Usuario userToBeEdited = userUtils.getUserById(id);

        String redirectUrl = wasUserDataChanged(updateUserNameLoginAndEmailRequest, attributes, userToBeEdited);

        if (redirectUrl != null) {
            return redirectUrl;
        }

        userMapper.toUpdateUsuario(userToBeEdited, updateUserNameLoginAndEmailRequest);

        usuarioRepository.save(userToBeEdited);
        attributes.addFlashAttribute("message", "User updated successfully!");

        return "redirect:/infos";
    }

    @Override
    public String deleteUser(String id) {
        usuarioRepository.delete(userUtils.getUserById(id));
        SecurityContextHolder.getContext().setAuthentication(null);

        return "redirect:/index";
    }

    private String wasUserDataChanged(
            UpdateUserNameLoginAndEmailRequest updateUserNameLoginAndEmailRequest,
            RedirectAttributes redirectAttributes,
            Usuario userToBeEdited) {

        boolean dataChanged = !userToBeEdited.getNome().equals(updateUserNameLoginAndEmailRequest.getNome())
                || !userToBeEdited.getEmail().equals(updateUserNameLoginAndEmailRequest.getEmail())
                || !userToBeEdited.getLogin().equals(updateUserNameLoginAndEmailRequest.getLogin());

        if (!dataChanged) {
            redirectAttributes.addFlashAttribute("message", "No changes were made.");
            return "redirect:/infos";
        }

        return null;
    }

    @Override
    public List<Community> getUserCommunities(String username) {
        Usuario user = usuarioRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return user.getMembros().stream().map(Membros::getCommunity).collect(Collectors.toList());
    }

    @Override
    public List<UserListResponse> listAllUserBasedOnQuery(String query) {
        List<Usuario> usuarios = getUsuarios(query);
        return convertToUserDtoList(usuarios);
    }

    private List<Usuario> getUsuarios(String query) {
        if (query != null && !query.isEmpty()) {
            return buscarUsuarios(query);
        } else {
            return listarUsuarios(Sort.by(Sort.Direction.ASC, "id"));
        }
    }

    private List<UserListResponse> convertToUserDtoList(List<Usuario> usuarios) {
        return usuarios.stream().map(UserListResponse::fromUsuario).collect(Collectors.toList());
    }

    public List<Usuario> buscarUsuarios(String query) {
        return usuarioRepository.findByNomeContainingIgnoreCase(query);
    }

    public List<Usuario> listarUsuarios(Sort sort) {
        return sort != null ? usuarioRepository.findAll(sort) : Collections.emptyList();
    }

    @Override
    public List<Livro> getUserLivros(String username) {
        Usuario user = usuarioRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return user.getLivros().stream().collect(Collectors.toList());
    }

    @Override
    public Usuario getUserByEmail(String email) {
        return usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email não encontrado"));
    }

    @Override
    public void saveUser(Usuario user) {
        usuarioRepository.save(user);
    }

    @Override
    public String verifyEmail(String token) {
        Optional<VerificationToken> verificationToken = tokenService.findByToken(token);

        // Usuario usuario = verificationToken.get().getUsuario();

        // var test = verificationToken.get().getUsuario().is_Enabled();

        if (verificationToken.isPresent()
                && verificationToken.get().getUsuario().is_Enabled()) {
            return "redirect:/login?verified";
        }

        String verificationResult = tokenService.validateToken(
                verificationToken.get().getToken()); // TODO - Isso não me parece certo. Talvez usar isso:
        // verificationToken.get().getToken()

        if (verificationResult.equalsIgnoreCase("invalid")) {
            return "redirect:/error?invalid";
        } else if (verificationResult.equalsIgnoreCase("expired")) {
            return "redirect:/error?expired";
        } else if (verificationResult.equalsIgnoreCase("valid")) {
            return "redirect:/login?verified";
        }
        return "redirect:/error?invalid";
    }

    @Override
    public void updateUserPassword(String id, String novaSenha) {
        Usuario user = usuarioRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Usuário não encontrado"));

        String senhaCriptografada = passwordEncoder.encode(novaSenha);
        user.setSenha(senhaCriptografada);

        usuarioRepository.save(user);
    }

    @Override
    public List<Usuario> listUser(Sort sort) {
        return usuarioRepository.findAll(sort);
    }
}
