package com.tcc.entrepaginas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.domain.Livro;
import com.tcc.entrepaginas.domain.Usuario;

public interface LivroRepository extends JpaRepository<Livro, String> {
  Livro findByNome(String nome);

  List<Livro> findByUsuario(Usuario usuario);
}