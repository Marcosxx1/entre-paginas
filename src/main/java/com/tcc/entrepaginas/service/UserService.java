package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserService {

    String registerAndRedirect(Authentication authentication, Model model);

    String saveUserFromForm(
            NovoUsuarioRequest novoUsuarioRequest, BindingResult result, RedirectAttributes attributes, Model model);
}
