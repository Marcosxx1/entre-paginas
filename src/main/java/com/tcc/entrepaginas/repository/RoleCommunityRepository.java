package com.tcc.entrepaginas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.domain.RoleCommunity;

public interface RoleCommunityRepository extends JpaRepository<RoleCommunity, String> {
    RoleCommunity findByPapel(String nome);
}