package com.tcc.entrepaginas.service.member;

import com.tcc.entrepaginas.domain.dto.MembersFromCommunityResponse;
import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.domain.entity.RoleCommunity;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface MemberService {

    void saveMember(Membros membros);

    List<MembersFromCommunityResponse> getMembersByCommunityId(String communityId);

    void removeMemberFromCommunity(String memberId, Authentication authentication);

    String updateMemberAuthorities(String memberId, String newComunnityRole);

    void addMemberToCommunity(String communityId, String userId);

    public List<RoleCommunity> getAllRoles();
}
