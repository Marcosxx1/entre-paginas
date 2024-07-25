package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.entity.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, String> {
    Livro findByNome(String nome);

    List<Livro> findByUsuario(Usuario usuario);
}
