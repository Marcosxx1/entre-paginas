package com.tcc.entrepaginas.domain.dto;

import com.tcc.entrepaginas.domain.entity.Usuario;

public record UserListResponse(String id, String nome, String login) {

    public static UserListResponse fromUsuario(Usuario usuario) {
        if (usuario != null && usuario.getPapel() != null) {
            return new UserListResponse(usuario.getId(), usuario.getNome(), usuario.getLogin());
        } else {
            return null;
        }
    }
}
