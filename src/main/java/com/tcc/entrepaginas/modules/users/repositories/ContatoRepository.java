
package com.tcc.entrepaginas.modules.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.modules.users.entities.Contato;

public interface ContatoRepository extends JpaRepository<Contato, String> {

}
