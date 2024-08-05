package com.tcc.entrepaginas.mapper.member;

import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.domain.entity.RoleCommunity;
import com.tcc.entrepaginas.domain.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public List<Membros> toListOfMembers(Usuario usuario, RoleCommunity roleCommunity, Community community) {

        List<Membros> membros = new ArrayList<>();

        membros.add(Membros.builder()
                .usuario(usuario)
                .roleCommunity(roleCommunity)
                .community(community)
                .build());

        return membros;
    }
}
