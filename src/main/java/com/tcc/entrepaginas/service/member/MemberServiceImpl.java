package com.tcc.entrepaginas.service.member;

import com.tcc.entrepaginas.domain.dto.MembersFromCommunityResponse;
import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.mapper.member.MemberMapper;
import com.tcc.entrepaginas.repository.MembrosRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MembrosRepository membrosRepository;
    private final MemberMapper memberMapper;

    @Override
    public void saveMember(Membros membros) {
        membrosRepository.save(membros);
    }

    @Override
    public List<MembersFromCommunityResponse> getMembersByCommunityId(String communityId) {
        var members = membrosRepository
                .findByCommunityId(communityId)
                .orElseThrow(() -> new ResourceNotFound("TODO -  SEM USUÁRIOS PARA COMUNIDADE"));

        return memberMapper.toListOfMembersFromCommunityResponse(members);
    }
}
