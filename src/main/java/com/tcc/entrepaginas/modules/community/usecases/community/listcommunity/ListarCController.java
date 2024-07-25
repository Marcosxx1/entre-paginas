package com.tcc.entrepaginas.modules.community.usecases.community.listcommunity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.entrepaginas.domain.Community;
import com.tcc.entrepaginas.repository.CommunityService;

@RestController
@RequestMapping("/community")
public class ListarCController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/list")
    public List<String> listarCommunity(@RequestParam(required = false) String query) {
        List<Community> communities;

        if (query != null && !query.isEmpty()) {
            communities = communityService.buscarComunidades(query);
        } else {
            communities = communityService.listarCommunities(Sort.by(Sort.Direction.ASC, "id"));
        }
        List<String> resultados = new ArrayList<>();
        for (Community community : communities) {
            resultados.add(community.getTitle());
        }

        return resultados;
    }
}
