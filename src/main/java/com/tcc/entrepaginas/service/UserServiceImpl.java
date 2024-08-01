package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.dto.UpdateUserNameLoginAndEmailRequest;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.mapper.user.UserMapper;
import com.tcc.entrepaginas.modules.users.record.UserDto;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import com.tcc.entrepaginas.utils.imageupload.ImageUtils;
import com.tcc.entrepaginas.utils.user.RegistroDeUsuario;
import com.tcc.entrepaginas.utils.user.UserUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
            NovoUsuarioRequest novoUsuarioRequest, BindingResult result, RedirectAttributes attributes, Model model) {
        registroDeUsuario.validarUsuario(novoUsuarioRequest, result);

        if (result.hasErrors()) {
            model.addAttribute("novoUsuarioRequest", novoUsuarioRequest);
            return "CadastrarUsuario";
        }

        Usuario usuario = userMapper.toUsuario(novoUsuarioRequest, passwordEncoder);
        usuarioRepository.save(usuario);

        attributes.addFlashAttribute("mensagem", "Cadastro efetuado com sucesso!");
        return "redirect:/login";
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

        return "redirect:/perfil";
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
            return "redirect:/infos/" + id;
        }

        Usuario userToBeEdited = userUtils.getUserById(id);

        String redirectUrl = wasUserDataChanged(updateUserNameLoginAndEmailRequest, attributes, userToBeEdited);

        if (redirectUrl != null) {
            return redirectUrl;
        }

        userMapper.toUpdateUsuario(userToBeEdited, updateUserNameLoginAndEmailRequest);

        usuarioRepository.save(userToBeEdited);
        attributes.addFlashAttribute("message", "User updated successfully!");

        return "redirect:/Perfil";
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
            return "redirect:/infos/" + userToBeEdited.getId();
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
    public List<UserDto> listAllUserBasedOnQuery(String query) {
        List<Usuario> usuarios = getUsuarios(query);
        return convertToUserDtoList(usuarios);
    }

    private List<Usuario> getUsuarios(String query) {// TODO - 1/3 Verificar se podemos separar isso daqui. Apesar que não sei se é necessário mesmo só para "limpar" o service
        if (query != null && !query.isEmpty()) {
            return buscarUsuarios(query);
        } else {
            return listarUsuarios(Sort.by(Sort.Direction.ASC, "id"));
        }
    }

    private List<UserDto> convertToUserDtoList(List<Usuario> usuarios) {// TODO - 3/3
        return usuarios.stream()
                .map(UserDto::fromUsuario)
                .collect(Collectors.toList());
    }

    public List<Usuario> buscarUsuarios(String query) {// TODO - 3/3
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

}
