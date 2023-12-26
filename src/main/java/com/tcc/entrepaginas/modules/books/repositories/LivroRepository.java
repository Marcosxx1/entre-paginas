package com.tcc.entrepaginas.modules.books.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.modules.books.entities.Livro;
import com.tcc.entrepaginas.modules.users.entities.Usuario;

public interface LivroRepository extends JpaRepository<Livro, String> {
  Livro findByNome(String nome);

  List<Livro> findByUsuario(Usuario usuario);
}