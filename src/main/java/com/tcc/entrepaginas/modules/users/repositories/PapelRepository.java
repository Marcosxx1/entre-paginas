package com.tcc.entrepaginas.modules.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.modules.users.entities.Papel;

public interface PapelRepository extends JpaRepository<Papel, String> {
    Papel findByPapelNome(String papelNome);
}
