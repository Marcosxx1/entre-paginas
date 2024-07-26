package com.tcc.entrepaginas.domain.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import org.springframework.security.core.userdetails.User;

//Classe customizada para podermos retornar o ID no contexto da Autenticação TODO -

@Getter
public class CustomUserDetails extends User {
    private String userId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String userId) {
        super(username, password, authorities);
        this.userId = userId;
    }

}
