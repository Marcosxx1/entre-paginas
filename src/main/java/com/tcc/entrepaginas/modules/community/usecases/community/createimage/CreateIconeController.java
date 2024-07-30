package com.tcc.entrepaginas.modules.community.usecases.community.createimage;

import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.service.CommunityServiceNew;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/community")
public class CreateIconeController {

    @Autowired
    private CommunityServiceNew communityServiceOld;

    @PostMapping("/icone/{id}")
    public ResponseEntity<String> createIcone(
            @PathVariable("id") String idComunidade,
            @RequestParam MultipartFile icone,
            HttpServletRequest request,
            Model model,
            RedirectAttributes attributes) {

        Community community = communityServiceOld.pegarCommunity(idComunidade);

        String fileName = communityServiceOld.atualizarIconeComunidade(icone);
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        String url = baseUrl + "/icone/" + fileName;

        community.setIcone(url);

        communityServiceOld.atualizarComunidade(community);

        return ResponseEntity.ok("Success");
    }
}
