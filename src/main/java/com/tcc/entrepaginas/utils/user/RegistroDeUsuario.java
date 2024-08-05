package com.tcc.entrepaginas.utils.user;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.domain.registration.RegistrationCompleteEvent;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import com.tcc.entrepaginas.utils.registration.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class RegistroDeUsuario {

    private final UsuarioRepository usuarioRepository;
    private final ApplicationEventPublisher publisher;

    public void validarUsuario(NovoUsuarioRequest novoUsuarioRequest, BindingResult result, HttpServletRequest request) {
        Usuario usuarioExistente =
                usuarioRepository.findByLoginOrEmail(novoUsuarioRequest.getLogin(), novoUsuarioRequest.getEmail());

        if (usuarioExistente != null) {
            if (usuarioExistente.getLogin().equals(novoUsuarioRequest.getLogin())) {
                result.rejectValue("login", "error.login", "User already exists with this login");
            }
            if (usuarioExistente.getEmail().equals(novoUsuarioRequest.getEmail())) {
                result.rejectValue("email", "error.email", "User already exists with this email");
            } else
            if(!usuarioExistente.isEnabled()){
                result.rejectValue("email", "error.email", "A new validation email was sent to your email");
                publisher.publishEvent(new RegistrationCompleteEvent(usuarioExistente, UrlUtils.getApplicationUrl(request)));

            }
        }
    }
}
