package com.tcc.entrepaginas.modules.community.record;

import com.tcc.entrepaginas.modules.community.entities.Reaction;

public record ReactionDto(String id, String reacao) {

    public static ReactionDto fromPost(Reaction reaction) {
        return new ReactionDto(
                reaction.getId(),
                reaction.getReacao());
    }

}