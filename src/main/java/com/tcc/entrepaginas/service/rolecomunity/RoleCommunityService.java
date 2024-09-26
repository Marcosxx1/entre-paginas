package com.tcc.entrepaginas.service.rolecomunity;

import com.tcc.entrepaginas.domain.entity.RoleCommunity;
import java.util.List;

public interface RoleCommunityService {

    RoleCommunity findCommunityByUserRole(String role);

    public List<RoleCommunity> listAllRoles();
}
