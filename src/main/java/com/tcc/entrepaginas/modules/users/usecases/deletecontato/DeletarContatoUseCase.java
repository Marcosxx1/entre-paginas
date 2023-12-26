package com.tcc.entrepaginas.modules.users.usecases.deletecontato;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.users.entities.Contato;
import com.tcc.entrepaginas.modules.users.repositories.ContatoRepository;

@Service
public class DeletarContatoUseCase {

    @Autowired
    private final ContatoRepository contatoRepository;

    public DeletarContatoUseCase(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    public Contato pegaContatoPorId(String id) {
        Optional<Contato> contato = contatoRepository.findById(id);
        return contato.orElseThrow(() -> new ResourceNotFound(id));
    }

    public boolean deletarContato(String id) {
        Contato contato = this.pegaContatoPorId(id);
        if (contato != null) {
            contatoRepository.delete(contato);
            return true;
        } else {
            return false;
        }
    }
}
