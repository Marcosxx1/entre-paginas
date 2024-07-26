package com.tcc.entrepaginas.utils;

import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class GetUserIdFromContext {

    private final UsuarioRepository userRepository;

    public String get() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public Usuario findAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = authentication.getName();
        return getUserById(userId);
    }

    public Usuario getUserById(String userId) {
        log.error("getUserById Exception error: [{}]", userId);
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound(userId));
    }
}
