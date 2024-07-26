package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import com.tcc.entrepaginas.repository.CommunityRepository;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommunityServiceImplNew implements CommunityServiceNew {

    private final CommunityRepository communityRepository;
    private final UsuarioService usuarioService;

    public List<Community> listarRandomCommunities(int totalItems, Principal principal) {
        List<Community> comunidades = communityRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        if (principal != null) {
            String username = principal.getName();
            List<Community> userCommunities = usuarioService.getUserCommunities(username);
            comunidades.removeAll(userCommunities);
        }

        if (comunidades.size() > totalItems) {
            comunidades = getRandomCommunities(comunidades, totalItems);
        }

        return comunidades;
    }

    public List<Community> getRandomCommunities(List<Community> comunidades, int totalItems) {
        Collections.shuffle(comunidades);
        return comunidades.subList(0, totalItems);
    }
}
