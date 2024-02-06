package com.tcc.entrepaginas.modules.community.usecases.community.mycommunity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tcc.entrepaginas.modules.community.entities.Community;
import com.tcc.entrepaginas.modules.community.service.CommunityService;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;

@Controller
@RequestMapping("/community")
public class MyCController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CommunityService communityService;

    @GetMapping("/mycommunities/{id}")
    public String getMyCommunities(@PathVariable("id") String idUsuario, Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario user = usuarioRepository.findByLogin(username);
            model.addAttribute("user", user);
        }

        List<Community> adminCommunities = communityService.listarCommunitiesPorUsuario(idUsuario, "ADMIN");
        List<Community> moderatorCommunities = communityService.listarCommunitiesPorUsuario(idUsuario, "MODERATOR");
        List<Community> userCommunities = communityService.listarCommunitiesPorUsuario(idUsuario, "USER");

        model.addAttribute("adminCommunities", adminCommunities);
        model.addAttribute("moderatorCommunities", moderatorCommunities);
        model.addAttribute("userCommunities", userCommunities);

        return "/MinhasComunidades";
    }
}
