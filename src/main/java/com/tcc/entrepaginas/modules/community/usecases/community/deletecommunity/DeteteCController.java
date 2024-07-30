package com.tcc.entrepaginas.modules.community.usecases.community.deletecommunity;

import com.tcc.entrepaginas.service.CommunityServiceNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
//@RequestMapping("/community")
public class DeteteCController {

    @Autowired
    private CommunityServiceNew communityServiceOld;

    @GetMapping("/delete/{id}")
    public String deletarLivro(@PathVariable("id") String idComunidade, RedirectAttributes attributes, Model model) {

        communityServiceOld.apagarComunidadePorId(idComunidade);

        return "redirect:/perfil";
    }
}
