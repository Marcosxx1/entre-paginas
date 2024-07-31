package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Reaction;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.service.PostServiceNew;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostServiceNew postService;

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

    public ResponseEntity<?> reacaoPost(String idPost, String reacao) {
        Post post = postService.buscarPost(idPost);

        if (post == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }

        Optional<Reaction> existingReaction =
                usuarioJaVotou(idPost, post.getUsuario().getId());

        if (existingReaction.isPresent()) {
            // If the reaction exists, delete it
            reactionRepository.delete(existingReaction.get());
        } else {
            // If no reaction exists, create a new one
            Reaction newReaction = Reaction.builder()
                    .reacao(reacao)
                    .usuario(post.getUsuario())
                    .post(post)
                    .build();
            reactionRepository.save(newReaction);
        }

        int newLikeCount = this.countReaction();
        return ResponseEntity.ok(newLikeCount);
    }

    public Optional<Reaction> usuarioJaVotou(String idPost, String idUsuario) {
        return reactionRepository.findByPostIdAndUsuarioId(idPost, idUsuario);
    }
}
