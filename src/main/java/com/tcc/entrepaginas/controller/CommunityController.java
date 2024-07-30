package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.NovaComunidadeRequest;
import com.tcc.entrepaginas.domain.dto.UpdateCommunityRequest;
import com.tcc.entrepaginas.service.CommunityServiceNew;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
@Slf4j
public class CommunityController {

    private final CommunityServiceNew communityService;

    @GetMapping("/community/{id}")
    public String community(@PathVariable String id, Model model, Authentication authentication)
            throws NullPointerException {
        return communityService.prepareCommunityAndListOfPosts(id, model, authentication);
    }

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

    @PostMapping("/icone/{id}")
    public ResponseEntity<String> createIcone(
            @PathVariable("id") String idComunidade, @RequestParam MultipartFile icone, HttpServletRequest request) {

        return communityService.changeCommunityIcon(idComunidade, icone, request);
    }

    @GetMapping("/list")
    public List<String> listarCommunity(@RequestParam(required = false) String query) {
        return communityService.listCommunitiesWithOrWithoutSort(query);
    }

    @PatchMapping("/{id}")
    public UpdateCommunityRequest updateCommunity(
            @PathVariable String id,
            @Valid @RequestBody UpdateCommunityRequest updateCommunityRequest,
            BindingResult result) {

        return communityService.updateCommunity(id, updateCommunityRequest, result);
    }

    @GetMapping("/delete/{id}")
    public String deletarLivro(@PathVariable("id") String idComunidade, RedirectAttributes attributes, Model model) {

        communityService.deleteCommunity(idComunidade);

        return "redirect:/perfil";
    }
}
