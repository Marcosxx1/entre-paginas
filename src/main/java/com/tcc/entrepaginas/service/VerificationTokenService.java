package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.domain.registration.VerificationToken;
import com.tcc.entrepaginas.repository.VerificationTokenRepository;

import java.util.Optional;

public interface VerificationTokenService {
    String validateToken(String token);
    void saveVerificationTokenForUser(Usuario usuario, String token);
    Optional<VerificationToken> findByToken(String token);
}
