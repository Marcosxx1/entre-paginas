package com.tcc.entrepaginas.modules.users.usecases.createcontato;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.entrepaginas.exceptions.CustomException;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.users.entities.Contato;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.record.ContactDto;
import com.tcc.entrepaginas.modules.users.repositories.ContatoRepository;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;

@Service
public class CriarContatoUseCase {

    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public CriarContatoUseCase(ContatoRepository contatoRepository,
            UsuarioRepository usuarioRepository) {
        this.contatoRepository = contatoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ContactDto criarContato(ContactDto contactDto, String idUsuario) {
        if (contactDto == null) {
            throw new CustomException("Contato inválido");
        }

        Contato novoContato = new Contato(contactDto.telefone(), this.pegarUsuario(idUsuario));

        contatoRepository.save(novoContato);

        return ContactDto.fromContato(novoContato);
    }

    public Usuario pegarUsuario(String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ResourceNotFound(id));
    }
}