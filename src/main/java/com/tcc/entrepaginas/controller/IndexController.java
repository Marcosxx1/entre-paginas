package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.service.IndexService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;

    @GetMapping("/login")
    public String login(Authentication authentication) {
        return indexService.redirecctToIndexOrLoginBasedOnAuth(authentication);
    }

    @GetMapping("/index")
    public String index(Model model, Principal principal, Authentication authentication) {
        return indexService.populateIndexModel(model, principal, authentication);
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication, Principal principal) {
        return indexService.populateModelForProfileView(model, authentication, principal);
    }

    @GetMapping("/infos")
    public String informacoesDoUsuario(Model model, Authentication authentication) {
        return indexService.prepareUser(model, authentication);
    }

    @GetMapping("/perfilVisitante/{userName}")
    public String perfilVisitante(Model model, @PathVariable("userName") String userName) {
        return indexService.visitOtherUsersFromIndex(model, userName);
    }
}
