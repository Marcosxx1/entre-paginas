package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.NovaComunidadeRequest;
import com.tcc.entrepaginas.domain.dto.SearchBarResponse;
import com.tcc.entrepaginas.domain.dto.UpdateCommunityRequest;
import com.tcc.entrepaginas.service.community.CommunityServiceNew;
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

    @GetMapping("/{id}")
    public String community(@PathVariable String id, Model model, Authentication authentication)
            throws NullPointerException {
        log.info(
                "CommunityController - GET on /community/{id}; /community/{} called by user: {}",
                id,
                authentication != null ? authentication.getName() : "Anonymous");
        return communityService.prepareCommunityAndListOfPosts(id, model, authentication);
    }

    @GetMapping("/create/{id}")
    public String populateCommunityToReturnToCreation(
            Model model, @PathVariable("id") String idUsuario, Authentication authentication) {
        log.info(
                "CommunityController - GET on /create/{id}; /create/{} called by user: {}",
                idUsuario,
                authentication != null ? authentication.getName() : "Anonymous");
        return communityService.beginCommunityCreation(model, idUsuario, authentication);
    }

    @PostMapping("/create/save/{id}")
    public String createCommunity(
            @PathVariable("id") String idUsuario,
            @Valid NovaComunidadeRequest novaComunidadeRequest,
            BindingResult result,
            Model model) {
        log.info(
                "CommunityController - POST on /create/save/{id}; /create/save/{} called with NovaComunidadeRequest: {}",
                idUsuario,
                novaComunidadeRequest);
        return communityService.salvarComunidade(model, result, novaComunidadeRequest, idUsuario);
    }

    @GetMapping("/mycommunities/{id}")
    public String getMyCommunities(@PathVariable("id") String idUsuario, Model model, Authentication authentication) {
        log.info(
                "CommunityController - GET on /mycommunities/{id}; /mycommunities/{} called by user: {}",
                idUsuario,
                authentication != null ? authentication.getName() : "Anonymous");
        return communityService.allMyCommunities(idUsuario, model, authentication);
    }

    @PostMapping("/icone/{id}")
    public String createIcone(
            @PathVariable("id") String idComunidade, @RequestParam MultipartFile icone, HttpServletRequest request) {
        log.info(
                "CommunityController - POST on /icone/{id}; /icone/{} called with MultipartFile: {}",
                idComunidade,
                icone != null ? icone.getOriginalFilename() : "No file");
        return communityService.changeCommunityIcon(idComunidade, icone, request);
    }

    @GetMapping("/list")
    public List<String> listarCommunity(@RequestParam(required = false) String query) {
        log.info("CommunityController - GET on /list; /list called with query: {}", query);
        return communityService.listCommunitiesWithOrWithoutSort(query);
    }

    @GetMapping("/search-bar/list")
    @ResponseBody
    public List<SearchBarResponse> listarCommunitySearchBar(@RequestParam(required = false) String query) {
        log.info("CommunityController - GET on /search-bar/list; /search-bar/list called with query: {}", query);
        return communityService.searchBar(query);
    }

    @PostMapping("/update")
    public String updateCommunity(
            @RequestParam("communityId") String id,
            @Valid @ModelAttribute UpdateCommunityRequest updateCommunityRequest,
            BindingResult result) {

        log.info(
                "CommunityController - PATCH on /{}; called with UpdateCommunityRequest: {}",
                id,
                updateCommunityRequest);
        return communityService.updateCommunity(id, updateCommunityRequest, result);
    }

    @GetMapping("/delete/{id}")
    public String deleteCommunity(@PathVariable("id") String idComunidade, RedirectAttributes attributes, Model model) {
        log.info("CommunityController - GET on /delete/{id}; /delete/{} called", idComunidade);
        return communityService.deleteCommunity(idComunidade);
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteCommunityAdmin(
            @PathVariable("id") String idComunidade, RedirectAttributes attributes, Model model) {
        log.info("CommunityController - GET on /delete/{id}; /delete/{} called", idComunidade);
        return communityService.deleteCommunityAdmin(idComunidade);
    }

    @GetMapping("/admin/delete/{communityId}/{memberId}")
    public String deleteUserAdmin(@PathVariable String communityId, @PathVariable String memberId) {
        log.info(
                "CommunityController - GET on /delete/{}/{}; called to delete member with id: {} from community with id: {}",
                communityId, memberId, memberId, communityId);
        return communityService.deleteMemberFromCommunity(communityId, memberId);
    }
}
