package com.tcc.entrepaginas.service.reaction;

import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Reaction;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.repository.ReactionRepository;
import com.tcc.entrepaginas.service.post.PostServiceNew;
import java.util.Optional;

import com.tcc.entrepaginas.utils.user.UserUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReactionServiceImplNew implements ReactionServiceNew {

    private final ReactionRepository reactionRepository;
    private final PostServiceNew postServiceNew;
    private final UserUtils userUtils;

    // public int countReaction() {
    // return reactionRepository.countByReacao("like");
    // }

    @Override
    public ResponseEntity<?> reacaoPost(String idPost, String reacao, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to vote.");
        }
        String userId = userUtils.getIdUserFromUserDetail(authentication);
        Usuario usuario = userUtils.getUsuarioObjectFromAuthentication(authentication);

        Post post = postServiceNew.buscarPost(idPost);

        Optional<Reaction> existingReaction = usuarioJaVotou(idPost, userId);

        if (existingReaction.isPresent()) {
            reactionRepository.delete(existingReaction.get());
        } else {
            Reaction newReaction = Reaction.builder()
                    .reacao(reacao)
                    .usuario(usuario)
                    .post(post)
                    .build();
            reactionRepository.save(newReaction);
        }

        int newLikeCount = reactionRepository.countByPostIdAndReacao(idPost, reacao);
        return ResponseEntity.ok(newLikeCount);
    }

    @Override

    public Optional<Reaction> usuarioJaVotou(String idPost, String idUsuario) {
        return reactionRepository.findByPostIdAndUsuarioId(idPost, idUsuario);
    }
}
