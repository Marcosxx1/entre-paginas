package com.tcc.entrepaginas.modules.community.usecases.community.listcommunity;

import com.tcc.entrepaginas.domain.entity.Community;
import java.util.ArrayList;
import java.util.List;

import com.tcc.entrepaginas.service.CommunityServiceNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/community")
public class ListarCController {

    @Autowired
    private CommunityServiceNew communityServiceOld;

    @GetMapping("/list")
    public List<String> listarCommunity(@RequestParam(required = false) String query) {
        List<Community> communities;

        if (query != null && !query.isEmpty()) {
            communities = communityServiceOld.buscarComunidades(query);
        } else {
            communities = communityServiceOld.listarCommunities(Sort.by(Sort.Direction.ASC, "id"));
        }
        List<String> resultados = new ArrayList<>();
        for (Community community : communities) {
            resultados.add(community.getTitle());
        }

        return resultados;
    }
}
