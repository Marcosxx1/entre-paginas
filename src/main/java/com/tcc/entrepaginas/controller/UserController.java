package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.dto.UpdateUserNameLoginAndEmailRequest;
import com.tcc.entrepaginas.domain.dto.UserListResponse;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(Authentication authentication, Model model) {
        log.info(
                "UserController - GET on /register; called by user: {}",
                authentication != null ? authentication.getName() : "Anonymous");
        return userService.registerAndRedirect(authentication, model);
    }

    @PostMapping("/register/save")
    public String createUser(
            @Valid NovoUsuarioRequest novoUsuarioRequest,
            BindingResult result,
            RedirectAttributes attributes,
            Model model,
            HttpServletRequest request) {
        log.info("UserController - POST on /register/save; NovoUsuarioRequest: {}", novoUsuarioRequest);
        return userService.saveUserFromForm(novoUsuarioRequest, result, attributes, model, request);
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        log.info("UserController - GET on /verifyEmail; called with token: {}", token);
        return userService.verifyEmail(token);
    }

    @PostMapping("/edit/{id}")
    public String atualizarNomeLoginOuEmailUsuario(
            @PathVariable String id,
            @Valid @ModelAttribute("updateUserNameLoginAndEmailRequest")
                    UpdateUserNameLoginAndEmailRequest updateUserNameLoginAndEmailRequest,
            @ModelAttribute("user") Usuario user,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        log.info(
                "UserController - POST on /edit/{id}; /edit/{}; UpdateUserNameLoginAndEmailRequest: {}",
                id,
                updateUserNameLoginAndEmailRequest);
        return userService.updateUserNameLoginAndEmail(
                user, id, updateUserNameLoginAndEmailRequest, result, redirectAttributes, model);
    }

    @PostMapping("/edit-password/{id}")
    public String atualizarSenhaUsuario(
            @PathVariable String id,
            @RequestParam("senha") String senha,
            @RequestParam("confirmarSenha") String confirmarSenha,
            RedirectAttributes redirectAttributes) {

        if (!senha.equals(confirmarSenha)) {
            redirectAttributes.addFlashAttribute("error", "As senhas não coincidem.");
            return "redirect:/user/edit-password/" + id;
        }

        try {
            userService.updateUserPassword(id, senha);
            redirectAttributes.addFlashAttribute("message", "Senha atualizada com sucesso!");
            return "redirect:/infos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar a senha.");
            return "redirect:/user/edit-password/" + id;
        }
    }

    @GetMapping("/delete/{id}")
    public String deletarUsuario(@PathVariable String id) {
        log.info("UserController - GET on /delete/{}; called to delete user with id: ", id);
        return userService.deleteUser(id);
    }

    @PostMapping("/image/{id}")
    public String createImage(
            Authentication authentication, @RequestParam("imagem") MultipartFile image, HttpServletRequest request) {
        log.info(
                "UserController - POST on /createImage/ID; called by user: {}",
                authentication != null ? authentication.getName() : "Anonymous");

        return userService.saveUserImage(authentication, image, request);
    }

    @GetMapping("/user/list")
    public List<UserListResponse> listarUsuarios(@RequestParam(required = false) String query) {

        log.info("UserController - GET on /user/list; /user/list called with query: {}", query);

        return userService.listAllUserBasedOnQuery(query);
    }

    @GetMapping("/user-search-bar/list")
    @ResponseBody
    public List<UserListResponse> usersSearchBar(@RequestParam(required = false) String query) {

        log.info("UserController - GET on /user-search-bar/list; /user-search-bar/list called with query: {}", query);

        return userService.listAllUserBasedOnQuery(query);
    }
}
