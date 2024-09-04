package com.tcc.entrepaginas.mapper.member;

import com.tcc.entrepaginas.domain.dto.MembersFromCommunityResponse;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.domain.entity.RoleCommunity;
import com.tcc.entrepaginas.domain.entity.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    public MembersFromCommunityResponse toMemberFromCommunityResponse(Membros membros) {

        return MembersFromCommunityResponse.builder()
                .memberId(membros.getId())
                .userId(membros.getUsuario().getId())
                .communityRole(membros.getRoleCommunity().getPapel())
                .communityId(membros.getCommunity().getId())
                .build();
    }

    public List<MembersFromCommunityResponse> toListOfMembersFromCommunityResponse(List<Membros> membros) {
        return membros.stream().map(this::toMemberFromCommunityResponse).collect(Collectors.toList());
    }

    /*    public List<MembersFromCommunityResponse> toListOfMembersFromCommunityResponse(List<Membros> membros) {

        List<MembersFromCommunityResponse> membersFromCommunityResponse = new ArrayList<>();

        membros.forEach(membro -> {
            membersFromCommunityResponse.add(toMemberFromCommunityResponse(membro));
        });

        return membersFromCommunityResponse;
    }*/

    /*    public List<MembersFromCommunityResponse> toListOfMembersFromCommunityResponse(List<Membros> membros) {

        List<MembersFromCommunityResponse> membersFromCommunityResponse = new ArrayList<>(membros.size());

        membros.forEach(membro -> membersFromCommunityResponse.add(toMemberFromCommunityResponse(membro)));

        return membersFromCommunityResponse;
    }*/

}
