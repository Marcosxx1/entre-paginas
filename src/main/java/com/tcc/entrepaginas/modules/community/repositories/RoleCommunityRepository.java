package com.tcc.entrepaginas.modules.community.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.modules.community.entities.RoleCommunity;

public interface RoleCommunityRepository extends JpaRepository<RoleCommunity, String> {
    RoleCommunity findByPapel(String nome);
}