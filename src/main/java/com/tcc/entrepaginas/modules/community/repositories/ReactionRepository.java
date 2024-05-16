package com.tcc.entrepaginas.modules.community.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.modules.community.entities.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction, String> {

    int countByReacao(String string);

    int countByPostIdAndReacao(String postId, String reacao);

}
