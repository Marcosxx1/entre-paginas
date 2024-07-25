package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PapelRepository extends JpaRepository<Papel, String> {
    Papel findByPapelNome(String papelNome);
}
