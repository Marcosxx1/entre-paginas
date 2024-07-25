package com.tcc.entrepaginas.modules.users.usecases.deletecontato;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contatos")
public class DeletarContatoController {

    @Autowired
    private final DeletarContatoUseCase deletarContatoUseCase;

    public DeletarContatoController(DeletarContatoUseCase deletarContatoUseCase) {
        this.deletarContatoUseCase = deletarContatoUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarContato(@PathVariable String id) {
        boolean deletionSuccessful = deletarContatoUseCase.deletarContato(id);
        if (deletionSuccessful) {
            return ResponseEntity.ok("Contato deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contato não encontrado.");
        }
    }
}
