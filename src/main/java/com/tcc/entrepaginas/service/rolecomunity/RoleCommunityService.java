package com.tcc.entrepaginas.service.rolecomunity;

import java.util.List;

import com.tcc.entrepaginas.domain.entity.RoleCommunity;

public interface RoleCommunityService {

    RoleCommunity findCommunityByUserRole(String role);

    public List<RoleCommunity> listAllRoles();
}
