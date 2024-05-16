package com.tcc.entrepaginas.modules.community.usecases.posts.reactionpost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.tcc.entrepaginas.modules.community.service.ReactionService;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;

@RestController
@RequestMapping("/posts")
public class ReactionPostController {

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/likes/{idPost}")
    public int likesPost(@PathVariable("idPost") String idPost, Authentication authentication) {
        Usuario user = new Usuario();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            user = usuarioRepository.findByLogin(username);
        }

        return reactionService.reacaoPost(idPost, "like", user.getId());
    }

    @PostMapping("/dislike/{idPost}")
    public int dislikePost(@PathVariable("idPost") String idPost, Authentication authentication) {
        Usuario user = new Usuario();

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            user = usuarioRepository.findByLogin(username);
        }

        return reactionService.reacaoPost(idPost, "dislike", user.getId());
    }
}
