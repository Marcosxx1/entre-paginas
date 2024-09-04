package com.tcc.entrepaginas.service.member;

import com.tcc.entrepaginas.domain.dto.MembersFromCommunityResponse;
import com.tcc.entrepaginas.domain.entity.Membros;
import java.util.List;

public interface MemberService {

    void saveMember(Membros membros);

    List<MembersFromCommunityResponse> getMembersByCommunityId(String communityId);
}
