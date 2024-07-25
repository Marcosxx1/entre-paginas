package com.tcc.entrepaginas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.domain.Papel;

public interface PapelRepository extends JpaRepository<Papel, String> {
    Papel findByPapelNome(String papelNome);
}
