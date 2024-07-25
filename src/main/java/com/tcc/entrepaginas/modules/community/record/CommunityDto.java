package com.tcc.entrepaginas.modules.community.record;

import com.tcc.entrepaginas.domain.entity.Community;
import java.time.Instant;

public record CommunityDto(String id, String title, String content, String icone, Boolean privado, Instant date) {

    public static CommunityDto fromCommunity(Community community) {
        return new CommunityDto(
                community.getId(),
                community.getTitle(),
                community.getContent(),
                community.getIcone(),
                community.getPrivado(),
                community.getDate());
    }
}
