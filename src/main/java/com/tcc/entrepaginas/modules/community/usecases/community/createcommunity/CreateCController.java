package com.tcc.entrepaginas.modules.community.usecases.community.createcommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.entrepaginas.modules.community.entities.Community;
import com.tcc.entrepaginas.modules.community.service.CommunityService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/community")
public class CreateCController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/create/{id}")
    public String comunidade(Model model, @PathVariable("id") String idUsuario) {

        model.addAttribute("community", new Community());
        model.addAttribute("idUsuario", idUsuario);

        return "/CriarComunidade";
    }

    @PostMapping("/create/save/{id}")
    public String createCommunity(@PathVariable("id") String idUsuario, @Valid Community community,
            BindingResult result, RedirectAttributes attributes, Model model) {

        communityService.salvarComunidade(community, idUsuario);

        return "redirect:/perfil";
    }

}
