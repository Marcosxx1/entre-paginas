package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.mapper.user.UserMapper;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import com.tcc.entrepaginas.utils.RegistroDeUsuario;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
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
}
