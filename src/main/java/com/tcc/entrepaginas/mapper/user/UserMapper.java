package com.tcc.entrepaginas.mapper.user;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.entity.Papel;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UsuarioRepository usuarioRepository;

    public Usuario toUsuario(NovoUsuarioRequest novoUsuarioRequest, PasswordEncoder passwordEncoder) {
        Papel papel = usuarioRepository.findPapelByNome("USER");

        return Usuario.builder()
                .nome(novoUsuarioRequest.getNome())
                .email(novoUsuarioRequest.getEmail())
                .login(novoUsuarioRequest.getLogin())
                .senha(passwordEncoder.encode(novoUsuarioRequest.getSenha()))
                .papel(papel)
                .build();
    }
}
