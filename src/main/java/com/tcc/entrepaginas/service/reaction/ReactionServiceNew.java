package com.tcc.entrepaginas.service.reaction;

import com.tcc.entrepaginas.domain.entity.Reaction;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface ReactionServiceNew {
    // int countReaction();

    ResponseEntity<?> reacaoPost(String idPost, String reacao, Authentication authentication);

    Optional<Reaction> usuarioJaVotou(String idPost, String idUsuario);
}
