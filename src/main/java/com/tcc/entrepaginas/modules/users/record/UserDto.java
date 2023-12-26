package com.tcc.entrepaginas.modules.users.record;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.tcc.entrepaginas.modules.users.entities.Usuario;

public record UserDto(String id, String imagem, String nome, String email, String login, String senha, boolean premium,
                Instant dataNascimento, RoleDto papel, List<ContactDto> contactsDtos) {

        public static UserDto fromUsuario(Usuario usuario) {
                if (usuario != null && usuario.getPapel() != null) {
                        return new UserDto(
                                        usuario.getId(),
                                        usuario.getImagem(),
                                        usuario.getNome(),
                                        usuario.getEmail(),
                                        usuario.getLogin(),
                                        usuario.getSenha(),
                                        usuario.isPremium(),
                                        usuario.getDataNascimento(),
                                        RoleDto.fromPapel(usuario.getPapel()),
                                        ContactDto.transformeContatoEmDto(
                                                        usuario.getContatos() != null ? usuario.getContatos()
                                                                        : new ArrayList<>())
                        /*
                         * PostDto.transformePostEmDto(usuario.getPosts() != null ? usuario.getPosts() :
                         * new ArrayList<>())
                         */
                        );
                } else {
                        return null;
                }
        }

}