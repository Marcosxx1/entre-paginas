package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.dto.UpdateUserNameLoginAndEmailRequest;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.mapper.user.UserMapper;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import com.tcc.entrepaginas.utils.UserUtils;
import com.tcc.entrepaginas.utils.RegistroDeUsuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RegistroDeUsuario registroDeUsuario;
    private final UserUtils userUtils;

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
}
