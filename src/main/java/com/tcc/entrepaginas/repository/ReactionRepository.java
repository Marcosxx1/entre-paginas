package com.tcc.entrepaginas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.domain.Post;
import com.tcc.entrepaginas.domain.Reaction;
import com.tcc.entrepaginas.domain.Usuario;

public interface ReactionRepository extends JpaRepository<Reaction, String> {

    int countByReacao(String string);

    int countByPostIdAndReacao(String postId, String reacao);

    Optional<Reaction> findByPostAndUsuario(Post post, Usuario usuario);

}
