package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(Authentication authentication, Model model) {
        return userService.registerAndRedirect(authentication, model);
    }

    @PostMapping("/register/save")
    public String createUser(
            @Valid NovoUsuarioRequest novoUsuarioRequest,
            BindingResult result,
            RedirectAttributes attributes,
            Model model) {

        return userService.saveUserFromForm(novoUsuarioRequest, result, attributes, model);
    }
}
