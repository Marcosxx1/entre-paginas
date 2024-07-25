package com.tcc.entrepaginas.modules.users.usecases.createcontato;

import com.tcc.entrepaginas.modules.users.record.ContactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contatos")
public class CriarContatoController {

    @Autowired
    private final CriarContatoUseCase criarContatoUseCase;

    public CriarContatoController(CriarContatoUseCase criarContatoUseCase) {
        this.criarContatoUseCase = criarContatoUseCase;
    }

    @PostMapping("/{id}")
    public ResponseEntity<ContactDto> criarContato(
            @RequestBody ContactDto contactDto, @PathVariable("id") String idUsuario) {
        ContactDto novoContactDto = criarContatoUseCase.criarContato(contactDto, idUsuario);
        return ResponseEntity.ok().body(novoContactDto);
    }
}
