package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Reaction;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import com.tcc.entrepaginas.service.PostServiceNew;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostServiceNew postService;

    @Autowired
    private UsuarioService usuarioService;

    public Reaction buscarReaction(String id) {
        Optional<Reaction> reaction = reactionRepository.findById(id);
        return reaction.orElseThrow(() -> new ResourceNotFound(id));
    }

    public List<Reaction> listarReactions(Sort sort) {
        return reactionRepository.findAll(sort);
    }

    public int countReaction() {
        int likesCount = reactionRepository.countByReacao("like");
        int dislikesCount = reactionRepository.countByReacao("dislike");

        return likesCount - dislikesCount;
    }

    public void apagarPostPorId(String id) {
        Reaction reaction = this.buscarReaction(id);
        reactionRepository.delete(reaction);
    }

    public int reacaoPost(String idPost, String reacao, String idUsuario) {
        Post post = postService.buscarPost(idPost);

        Usuario usuarioEncontrado = usuarioService.pegarUsuario(idUsuario);

        if (usuarioEncontrado == null) {
            throw new IllegalStateException("Usuário não existe.");
        }
        if (post == null) {
            throw new IllegalArgumentException("Post not found.");
        }

        /*
         * if (usuarioJaVotou(idPost, idUsuario)) {
         * throw new IllegalStateException("User has already voted on this post.");
         * }
         */
        Reaction reaction = new Reaction(reacao, usuarioEncontrado, post);

        reactionRepository.save(reaction);

        return this.countReaction();
    }

    public boolean usuarioJaVotou(String idPost, String idUsuario) {
        List<Reaction> reactions = listarReactions(Sort.by(Sort.Direction.ASC, "id"));

        for (Reaction reaction : reactions) {
            if (reaction.getPost().getId().equals(idPost)
                    && reaction.getUsuario().getId().equals(idUsuario)) {
                System.out.println("Usuário já votou.");
                return true;
            }
        }

        return false;
    }
}
