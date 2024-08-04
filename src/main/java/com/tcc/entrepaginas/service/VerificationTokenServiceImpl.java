package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.domain.registration.VerificationToken;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import com.tcc.entrepaginas.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UsuarioRepository userService;

    @Override
    public String validateToken(String token) {

        Optional<VerificationToken> existToken = verificationTokenRepository.findByToken(token);
        if (existToken.isEmpty()) {
            return "invalid";
        }

        Usuario usuario= existToken.get().getUsuario();
        Calendar cal = Calendar.getInstance();
        if ((existToken.get().getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
            return "expired";
        }

        usuario.setEnabled(true);
        userService.save(usuario);

        return "valid";
    }

    @Override
    public void saveVerificationTokenForUser(Usuario usuario, String token) {
        VerificationToken verificationToken = new VerificationToken(token, usuario);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }
}
