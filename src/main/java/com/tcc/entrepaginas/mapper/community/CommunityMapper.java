package com.tcc.entrepaginas.mapper.community;

import com.tcc.entrepaginas.domain.dto.NovaComunidadeRequest;
import com.tcc.entrepaginas.domain.dto.UpdateCommunityRequest;
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

    public Community fromUpdateRequestToCommunity(Community community, UpdateCommunityRequest updateCommunityRequest) {
        return Community.builder()
                .title(updateCommunityRequest.getTitle())
                .content(updateCommunityRequest.getContent())
                .privado(updateCommunityRequest.getPrivado())
                .build();
    }
}
