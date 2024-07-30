package com.tcc.entrepaginas.mapper;

import com.tcc.entrepaginas.domain.dto.NovaComunidadeRequest;
import com.tcc.entrepaginas.domain.entity.Community;
import org.springframework.stereotype.Component;

@Component
public class CommunityMapper {

    public Community toCommunity(NovaComunidadeRequest novaComunidadeRequest) {
        return Community.builder()
                .title(novaComunidadeRequest.getTitle())
                .content(novaComunidadeRequest.getContent())
                .privado(novaComunidadeRequest.getPrivado())
                .build();
    }
}
