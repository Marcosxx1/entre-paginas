package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.dto.UpdateUserNameLoginAndEmailRequest;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Usuario;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserService {

    String registerAndRedirect(Authentication authentication, Model model);

    String saveUserFromForm(
            NovoUsuarioRequest novoUsuarioRequest, BindingResult result, RedirectAttributes attributes, Model model);

    String updateUserNameLoginAndEmail(
            Usuario user,
            String id,
            UpdateUserNameLoginAndEmailRequest updateUserNameLoginAndEmailRequest,
            BindingResult result,
            RedirectAttributes attributes,
            Model model);

    String deleteUser(String id);

    List<Community> getUserCommunities(String username);
}
