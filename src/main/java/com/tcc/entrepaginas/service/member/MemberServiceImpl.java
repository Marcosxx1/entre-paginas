package com.tcc.entrepaginas.service.member;

import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.repository.MembrosRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MembrosRepository membrosRepository;

    @Override
    public void saveMember(Membros membros) {
        membrosRepository.save(membros);
    }

    @Override
    public Optional<List<Membros>> getMembersByCommunityId(String communityId) {
        return membrosRepository.findByCommunityId(communityId);
    }
}
