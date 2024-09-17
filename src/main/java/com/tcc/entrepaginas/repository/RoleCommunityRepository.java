package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.RoleCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleCommunityRepository extends JpaRepository<RoleCommunity, String> {
    RoleCommunity findByPapel(String nome);

    RoleCommunity findFirstByPapel(String papel);
}
