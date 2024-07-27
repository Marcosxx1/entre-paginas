package com.tcc.entrepaginas.utils;

import com.tcc.entrepaginas.domain.entity.CustomUserDetails;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Slf4j
@Component
@AllArgsConstructor
public class UserUtils {

    private final UsuarioRepository userRepository;

    public String get() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public Model setModelIfAuthenticationExists(Principal principal, Authentication authentication, Model model) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            String userId = customUserDetails.getUserId();
            Usuario user = getUserById(userId);
            model.addAttribute("user", user);
        }
        return model;
    }

    public Model setUserInAttributesIfAuthenticated(Model model, Authentication authentication, String username) {
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario user = getUserById(username);
            model.addAttribute("user", user);
        }
        return model;
    }

    public Usuario getUserById(String userId) {
        log.error("getUserById Exception error: [{}]", userId);
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound(userId));
    }

    /*
    public Usuario findAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = authentication.getName();
        return getUserById(userId);
    }*/
}
