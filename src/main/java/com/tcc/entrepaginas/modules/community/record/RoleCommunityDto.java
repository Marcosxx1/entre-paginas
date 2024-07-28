package com.tcc.entrepaginas.modules.community.record;

import com.tcc.entrepaginas.domain.entity.RoleCommunity;

public record RoleCommunityDto(String id, String papel) {

    public static RoleCommunityDto fromPapel(RoleCommunity papel) {
        return new RoleCommunityDto(papel.getId(), papel.getPapel());
    }
}
