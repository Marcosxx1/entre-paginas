package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Reaction;
import com.tcc.entrepaginas.repository.ReactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ReactionServiceImplNew implements ReactionServiceNew {

    private final ReactionRepository reactionRepository;
    private final PostServiceNew postServiceNew;

    public int countReaction() {
        int likesCount = reactionRepository.countByReacao("like");
        int dislikesCount = reactionRepository.countByReacao("dislike");

        return likesCount - dislikesCount;
    }

    @Override
    public ResponseEntity<?> reacaoPost(String idPost, String reacao) {
        Post post = postServiceNew.buscarPost(idPost);

        Optional<Reaction> existingReaction =
                usuarioJaVotou(idPost, post.getUsuario().getId());

        if (existingReaction.isPresent()) {

            reactionRepository.delete(existingReaction.get());
        } else {
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

    @Override
    public Optional<Reaction> usuarioJaVotou(String idPost, String idUsuario) {
        return reactionRepository.findByPostIdAndUsuarioId(idPost, idUsuario);
    }
}
