package com.tcc.entrepaginas.service;

import org.springframework.security.core.Authentication;

public interface IndexService {
    String redirecctToIndexOrLoginBasedOnAuth(Authentication authentication);
}
