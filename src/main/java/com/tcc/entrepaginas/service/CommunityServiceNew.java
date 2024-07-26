package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Community;
import java.security.Principal;
import java.util.List;

public interface CommunityServiceNew {
    List<Community> listarRandomCommunities(int totalItems, Principal principal);
}
