package com.tcc.entrepaginas.mapper.member;

import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.domain.entity.RoleCommunity;
import com.tcc.entrepaginas.domain.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Membros toMember(Usuario usuario, RoleCommunity roleCommunity, Community community) {
        return Membros.builder()
                .usuario(usuario)
                .roleCommunity(roleCommunity)
                .community(community)
                .build();
    }
}
