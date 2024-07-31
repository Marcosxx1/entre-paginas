package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Reaction;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ReactionServiceNew {
    int countReaction();
    ResponseEntity<?> reacaoPost(String idPost, String reacao);
    Optional<Reaction> usuarioJaVotou(String idPost, String idUsuario);
}
