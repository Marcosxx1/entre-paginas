package com.tcc.entrepaginas.modules.users.record;

import com.tcc.entrepaginas.domain.entity.Papel;

public record RoleDto(String id, String role) {

    public static RoleDto fromPapel(Papel papel) {
        return new RoleDto(papel.getId(), papel.getPapelNome());
    }
}
