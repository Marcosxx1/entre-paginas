package com.tcc.entrepaginas.modules.users.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.modules.users.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    Usuario findByLogin(String login);

    Usuario findByEmail(String email);

    List<Usuario> findByNomeContainingIgnoreCase(String query);

}