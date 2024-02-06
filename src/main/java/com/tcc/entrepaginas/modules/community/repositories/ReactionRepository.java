package com.tcc.entrepaginas.modules.community.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.modules.community.entities.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, String> {

    Reaction findByPostIdAndUsuarioId(String idPost, String idUsuario);

    int countByReacao(String string);

}
