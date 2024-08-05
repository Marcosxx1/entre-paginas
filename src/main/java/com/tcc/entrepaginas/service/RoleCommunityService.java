package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.RoleCommunity;

public interface RoleCommunityService {

    RoleCommunity findCommunityByUserRole(String role);
}
