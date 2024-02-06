package com.tcc.entrepaginas.modules.community.usecases.community.createcommunity;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.entrepaginas.modules.community.entities.Community;
import com.tcc.entrepaginas.modules.community.service.CommunityService;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/community")
public class CreateCController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CommunityService communityService;

    @GetMapping("/create/{id}")
    public String comunidade(Model model, @PathVariable("id") String idUsuario, Authentication authentication,
            Principal principal) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario user = usuarioRepository.findByLogin(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("community", new Community());
        model.addAttribute("idUsuario", idUsuario);

        return "/CriarComunidade";
    }

    @PostMapping("/create/save/{id}")
    public String createCommunity(@PathVariable("id") String idUsuario, @Valid Community community,
            BindingResult result, RedirectAttributes attributes, Model model) {

        communityService.salvarComunidade(community, idUsuario);

        return "redirect:/perfil";
    }

}
