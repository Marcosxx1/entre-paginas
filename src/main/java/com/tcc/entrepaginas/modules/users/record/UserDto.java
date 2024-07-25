package com.tcc.entrepaginas.modules.users.record;

import com.tcc.entrepaginas.domain.entity.Usuario;

public record UserDto(String id, String nome) {

    public static UserDto fromUsuario(Usuario usuario) {
        if (usuario != null && usuario.getPapel() != null) {
            return new UserDto(usuario.getId(), usuario.getNome());
        } else {
            return null;
        }
    }
}
