package com.tcc.entrepaginas.modules.community.usecases.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcc.entrepaginas.modules.community.service.CommunityService;
import com.tcc.entrepaginas.modules.community.service.PostService;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;

@Controller
public class CommunityController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    public CommunityService communityService;

    @Autowired
    public PostService postService;

    @GetMapping("/community/{id}")
    public String community(@PathVariable String id, Model model, Authentication authentication)
            throws NullPointerException {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario user = usuarioRepository.findByLogin(username);
            model.addAttribute("user", user);
        }

        model.addAttribute("listPost", postService.listPostsByCommunity(id));
        model.addAttribute("community", communityService.pegarCommunity(id));
        return "/Comunidade";
    }
}

