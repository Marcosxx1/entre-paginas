package com.tcc.entrepaginas.modules.community.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.modules.community.entities.Membros;

public interface MembrosRepository extends JpaRepository<Membros, String> {

}
