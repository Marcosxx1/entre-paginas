package com.tcc.entrepaginas.service.rolecomunity;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcc.entrepaginas.domain.entity.RoleCommunity;
import com.tcc.entrepaginas.repository.RoleCommunityRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleCommunityServiceImpl implements RoleCommunityService {

    private final RoleCommunityRepository roleCommunityRepository;

    @Override
    public RoleCommunity findCommunityByUserRole(String role) {
        // Throw exception when role is not provided
        if (role == null || role.isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        return roleCommunityRepository.findByPapel(role);
    }

    @Override
    public List<RoleCommunity> listAllRoles() {
        return roleCommunityRepository.findAll();
    }
}
