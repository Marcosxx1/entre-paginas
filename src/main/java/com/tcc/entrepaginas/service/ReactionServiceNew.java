package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Reaction;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

public interface ReactionServiceNew {
    int countReaction();

    ResponseEntity<?> reacaoPost(String idPost, String reacao);

    Optional<Reaction> usuarioJaVotou(String idPost, String idUsuario);
}
