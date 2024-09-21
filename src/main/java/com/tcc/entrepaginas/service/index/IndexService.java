package com.tcc.entrepaginas.service.index;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import com.tcc.entrepaginas.domain.entity.Livro;

public interface IndexService {
    String redirecctToIndexOrLoginBasedOnAuth(Authentication authentication);

    String populateIndexModel(Model model, Principal principal, Authentication authentication);

    String populateModelForProfileView(Model model, Authentication authentication, Principal principal);

    String prepareUser(Model model, Authentication authentication);

    String visitOtherUsersFromIndex(Model model, String userName, Authentication authentication);

    String suporte(Model model, Principal principal, Authentication authentication);

    String telaAdmin(Model model, Principal principal, Authentication authentication);

    String filter(Model model, Principal principal, Authentication authentication);

}
