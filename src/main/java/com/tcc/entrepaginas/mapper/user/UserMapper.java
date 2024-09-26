package com.tcc.entrepaginas.mapper.user;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import com.tcc.entrepaginas.domain.dto.UpdateUserNameLoginAndEmailRequest;
import com.tcc.entrepaginas.domain.entity.Papel;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import com.tcc.entrepaginas.utils.user.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UsuarioRepository usuarioRepository;
    private final UserUtils userUtils;

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

    public void toUpdateUsuario(
            Usuario existingUser, UpdateUserNameLoginAndEmailRequest updateUserNameLoginAndEmailRequest) {
        existingUser.setNome(updateUserNameLoginAndEmailRequest.getNome());
        existingUser.setEmail(updateUserNameLoginAndEmailRequest.getEmail());
        existingUser.setLogin(updateUserNameLoginAndEmailRequest.getLogin());
        existingUser.setCidade(updateUserNameLoginAndEmailRequest.getCidade());
        existingUser.setEstadoBrasil(EstadoBrasil.valueOf(updateUserNameLoginAndEmailRequest.getEstadoBrasil()));
    }

    public UpdateUserNameLoginAndEmailRequest toUpdateUserNameLoginAndEmailRequest(Authentication authentication) {
        return UpdateUserNameLoginAndEmailRequest.builder()
                .id(userUtils.getIdUserFromUserDetail(authentication))
                .nome(userUtils
                        .getUserById(userUtils.getIdUserFromUserDetail(authentication))
                        .getNome())
                .email(userUtils
                        .getUserById(userUtils.getIdUserFromUserDetail(authentication))
                        .getEmail())
                .login(userUtils
                        .getUserById(userUtils.getIdUserFromUserDetail(authentication))
                        .getLogin())
                .build();
    }
}
