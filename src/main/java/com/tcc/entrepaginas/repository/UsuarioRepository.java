package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Papel;
import com.tcc.entrepaginas.domain.entity.Usuario;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByLogin(String login);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByNomeContainingIgnoreCase(String query);

    @Query("SELECT p FROM Papel p WHERE p.papelNome = :papelNome")
    Papel findPapelByNome(@Param("papelNome") String papelNome);

    Usuario findByLoginOrEmail(String login, String email);
}
