package com.tcc.entrepaginas.modules.community.record;

import java.time.Instant;

import com.tcc.entrepaginas.modules.community.entities.Community;

public record CommunityDto(String id, String title, String content, String icone, Boolean privado,
        Instant date) {

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
