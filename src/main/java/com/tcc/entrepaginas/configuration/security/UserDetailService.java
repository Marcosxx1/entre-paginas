package com.tcc.entrepaginas.configuration.security;

import com.tcc.entrepaginas.domain.entity.CustomUserDetails;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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

            return new CustomUserDetails(usuario.getLogin(), usuario.getPassword(), papeisDoUsuario, usuario.getId());
        } else {
            log.error("UserDetailService.loadUserByUsername Exception error: [{}]", username);
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
    }
}
