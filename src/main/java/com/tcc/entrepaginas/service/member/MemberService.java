package com.tcc.entrepaginas.service.member;

import com.tcc.entrepaginas.domain.entity.Membros;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    void saveMember(Membros membros);

    Optional<List<Membros>> getMembersByCommunityId(String communityId);
}
