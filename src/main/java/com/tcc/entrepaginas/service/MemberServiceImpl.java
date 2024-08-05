package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.repository.MembrosRepository;
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
}
