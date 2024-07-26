package com.tcc.entrepaginas.domain.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;


//Classe customizada para podermos retornar o ID no contexto da Autenticação TODO -
public class CustomUserDetails implements UserDetails {

    private final String id;
    private final String username;
     private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String id, String username,  Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
         this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

