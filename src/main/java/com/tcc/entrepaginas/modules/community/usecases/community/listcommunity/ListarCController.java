package com.tcc.entrepaginas.modules.community.usecases.community.listcommunity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.entrepaginas.modules.community.entities.Community;
import com.tcc.entrepaginas.modules.community.service.CommunityService;

@RestController
@RequestMapping("/community")
public class ListarCController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/list")
    public String listarCommunity(Model model) {
        List<Community> communities = communityService.listarCommunities(Sort.by(Sort.Direction.ASC, "id"));
        
        model.addAttribute("communities", communities);
        return "/Alguma Coisa";
    }
}
