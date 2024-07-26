package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.dto.UpdateUserNameLoginAndEmailRequest;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/edit/{id}")
    public String atualizarUsuario(
            @PathVariable String id,
            @Valid @ModelAttribute("updateUserNameLoginAndEmailRequest")
                    UpdateUserNameLoginAndEmailRequest updateUserNameLoginAndEmailRequest,
            @ModelAttribute("user") Usuario user,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        return userService.updateUserNameLoginAndEmail(
                user, id, updateUserNameLoginAndEmailRequest, result, redirectAttributes, model);
    }

    @GetMapping("/delete/{id}")
    public String deletarUsuario(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}
