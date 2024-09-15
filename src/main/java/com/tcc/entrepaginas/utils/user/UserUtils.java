package com.tcc.entrepaginas.utils.user;

import com.tcc.entrepaginas.domain.entity.CustomUserDetails;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.repository.UsuarioRepository;
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

    public String getIdUserFromUserDetail(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getUserId();
        }
        return "";
    }

    public Model setModelIfAuthenticationExists(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return model.addAttribute("user", getUserById(getIdUserFromUserDetail(authentication)));
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
        return userId.equals("") ? null
                : userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound(userId));
    }

    public Usuario getUserByLogin(String userName) {
        return userRepository.findByLogin(userName);
    }

    public Usuario getUsuarioObjectFromAuthentication(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails customUserDetails) {
            return getUserById(customUserDetails.getUserId());
        }
        return null;
    }
}
