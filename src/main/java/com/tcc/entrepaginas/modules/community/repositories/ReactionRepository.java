package com.tcc.entrepaginas.modules.community.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.modules.community.entities.Post;
import com.tcc.entrepaginas.modules.community.entities.Reaction;
import com.tcc.entrepaginas.modules.users.entities.Usuario;

public interface ReactionRepository extends JpaRepository<Reaction, String> {

    int countByReacao(String string);

    int countByPostIdAndReacao(String postId, String reacao);

    Optional<Reaction> findByPostAndUsuario(Post post, Usuario usuario);

}
