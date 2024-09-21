package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Reaction;
import com.tcc.entrepaginas.domain.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReactionRepository extends JpaRepository<Reaction, String> {

    @Query("""
            SELECT COUNT(*) FROM Reaction rec \
            WHERE rec.post.id ILIKE :id \
            AND rec.reacao = :reacao\
            """)
    int countByReacao(@Param("id") String id, @Param("reacao") String reacao);

    int countByPostIdAndReacao(String postId, String reacao);

    Optional<Reaction> findByPostIdAndUsuarioId(String idPost, String idUsuario);

    Optional<Reaction> findByPostAndUsuario(Post post, Usuario usuario);
}