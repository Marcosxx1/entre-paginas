package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.NovaComunidadeRequest;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.service.CommunityServiceNew;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityServiceNew communityService;

    @GetMapping("/create/{id}")
    public String populateCommunityToReturnToCreation(
            Model model, @PathVariable("id") String idUsuario, Authentication authentication) {
        return communityService.beginCommunityCreation(model, idUsuario, authentication);
    }

    @PostMapping("/create/save/{id}")
    public String createCommunity(
            @PathVariable("id") String idUsuario,
            @Valid NovaComunidadeRequest novaComunidadeRequest,
            BindingResult result,
            Model model) {

        return communityService.salvarComunidade(model, result, novaComunidadeRequest, idUsuario);
    }

    @GetMapping("/mycommunities/{id}")
    public String getMyCommunities(@PathVariable("id") String idUsuario, Model model, Authentication authentication) {
        return communityService.allMyCommunities(idUsuario, model, authentication);
    }

}
