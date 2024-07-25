package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, String> {}
