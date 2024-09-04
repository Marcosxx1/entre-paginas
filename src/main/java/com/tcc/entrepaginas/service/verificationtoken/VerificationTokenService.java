package com.tcc.entrepaginas.service.verificationtoken;

import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.domain.registration.VerificationToken;
import java.util.Optional;

public interface VerificationTokenService {
    String validateToken(String token);

    void saveVerificationTokenForUser(Usuario usuario, String token);

    Optional<VerificationToken> findByToken(String token);
}
