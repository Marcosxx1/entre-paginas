package com.tcc.entrepaginas.modules.community.usecases.community.updatecommunity;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.entrepaginas.exceptions.CustomException;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.community.entities.Community;
import com.tcc.entrepaginas.modules.community.record.CommunityDto;
import com.tcc.entrepaginas.modules.community.repositories.CommunityRepository;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdateCUseCase {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UpdateCUseCase(CommunityRepository communityRepository,
            UsuarioRepository usuarioRepository) {
        this.communityRepository = communityRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public CommunityDto updateCommunity(String id, CommunityDto communityDto) {
        if (communityDto == null) {
            throw new CustomException("community invalid");
        }

        try {
            Community community = this.pegarComunidade(communityDto.id());

            community.setId(communityDto.id());
            community.setTitle(communityDto.title());
            community.setContent(communityDto.content());
            community.setDate(communityDto.date());

            communityRepository.save(community);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound(id);
        }

        return communityDto;
    }

    public Community pegarComunidade(String id) {
        Optional<Community> community = communityRepository.findById(id);
        return community.orElseThrow(() -> new ResourceNotFound(id));
    }

    public Usuario pegarUsuario(String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ResourceNotFound(id));
    }

}
