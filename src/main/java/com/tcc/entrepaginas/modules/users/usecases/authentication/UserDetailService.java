package com.tcc.entrepaginas.modules.users.usecases.authentication;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UserDetailService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByLogin(username);

        if (usuario != null) {
            Set<GrantedAuthority> papeisDoUsuario = new HashSet<>();

            GrantedAuthority pp = new SimpleGrantedAuthority(usuario.getPapel().getPapelNome());
            papeisDoUsuario.add(pp);

            return new User(usuario.getLogin(), usuario.getPassword(), papeisDoUsuario);
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }
}