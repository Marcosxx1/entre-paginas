package com.tcc.entrepaginas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    @Override
    public String redirecctToIndexOrLoginBasedOnAuth(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";
        }
        return "/Login";
    }
}
