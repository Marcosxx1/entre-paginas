package com.tcc.entrepaginas.service;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public interface IndexService {
    String redirecctToIndexOrLoginBasedOnAuth(Authentication authentication);

    String populateIndexModel(Model model, Principal principal, Authentication authentication);

    String populateModelForProfileView(Model model, Authentication authentication, Principal principal);

    String prepareUser(Model model, Authentication authentication);

    String visitOtherUsersFromIndex(Model model, String userName);

    String suporte(Model model, Principal principal, Authentication authentication);
}
