package com.tcc.entrepaginas.utils.roleCommunity;

import org.springframework.stereotype.Component;

import com.tcc.entrepaginas.domain.entity.RoleCommunity;
import com.tcc.entrepaginas.repository.RoleCommunityRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleCommunityUtils {

    private final RoleCommunityRepository roleCommunityRepository;

    public RoleCommunity getRoleCommunity(String roleCommunityString) {

        RoleCommunity roleCommunity = roleCommunityRepository.findByPapel(roleCommunityString);

        return roleCommunity;
    }

}
