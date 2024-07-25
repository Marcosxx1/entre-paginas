package com.tcc.entrepaginas.utils;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@RequiredArgsConstructor
public class RegistroDeUsuario {

    private final UsuarioRepository usuarioRepository;

    public void validarUsuario(NovoUsuarioRequest novoUsuarioRequest, BindingResult result) {
        Usuario usuarioExistente =
                usuarioRepository.findByLoginOrEmail(novoUsuarioRequest.getLogin(), novoUsuarioRequest.getEmail());

        if (usuarioExistente != null) {
            if (usuarioExistente.getLogin().equals(novoUsuarioRequest.getLogin())) {
                result.rejectValue("login", "error.login", "User already exists with this login");
            }
            if (usuarioExistente.getEmail().equals(novoUsuarioRequest.getEmail())) {
                result.rejectValue("email", "error.email", "User already exists with this email");
            }
        }
    }
}
