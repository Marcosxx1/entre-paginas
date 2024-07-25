package com.tcc.entrepaginas.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class GetUserIdFromContext {

    public String get() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
