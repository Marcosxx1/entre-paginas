package com.tcc.entrepaginas.modules.community.usecases.posts.reactionpost;

import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.repository.ReactionService;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class ReactionPostController {

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/like/{idPost}")
    public int likePost(@PathVariable("idPost") String idPost, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Usuario user = usuarioRepository.findByLogin(username);
            if (user != null) {
                return reactionService.reacaoPost(idPost, "like", user.getId());
            } else {
                throw new RuntimeException("User not found");
            }
        } else {
            throw new RuntimeException("User not authenticated");
        }
    }

    @PostMapping("/dislike/{idPost}")
    public int dislikePost(@PathVariable("idPost") String idPost, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Usuario user = usuarioRepository.findByLogin(username);
            if (user != null) {
                return reactionService.reacaoPost(idPost, "dislike", user.getId());
            } else {
                throw new RuntimeException("User not found");
            }
        } else {
            throw new RuntimeException("User not authenticated");
        }
    }
}
