package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Reaction;
import com.tcc.entrepaginas.domain.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, String> {

    int countByReacao(String string);

    int countByPostIdAndReacao(String postId, String reacao);

    Reaction findByPostIdAndUsuarioId(String idPost, String idUsuario);

    Optional<Reaction> findByPostAndUsuario(Post post, Usuario usuario);
}
