package com.tcc.entrepaginas.modules.community.usecases.community.deletecommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.entrepaginas.repository.CommunityService;

@RestController
@RequestMapping("/community")
public class DeteteCController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/delete/{id}")
    public String deletarLivro(@PathVariable("id") String idComunidade,
            RedirectAttributes attributes, Model model) {

        communityService.apagarComunidadePorId(idComunidade);

        return "redirect:/perfil";
    }
}
