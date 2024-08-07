package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovaComunidadeRequest;
import com.tcc.entrepaginas.domain.dto.UpdateCommunityRequest;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.CommunityNotFoundException;
import com.tcc.entrepaginas.mapper.community.CommunityMapper;
import com.tcc.entrepaginas.mapper.member.MemberMapper;
import com.tcc.entrepaginas.repository.CommunityRepository;
import com.tcc.entrepaginas.utils.PostUtils;
import com.tcc.entrepaginas.utils.community.CommunityUtils;
import com.tcc.entrepaginas.utils.user.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityServiceImplNew implements CommunityServiceNew {

    private final RoleCommunityService roleCommunityService;
    private final CommunityRepository communityRepository;

    private final CommunityMapper communityMapper;
    private final CommunityUtils communityUtils;
    private final PostUtils postUtils;
    private final UserService userService;
    private final MemberMapper memberMapper;
    private final UserUtils userUtils;

    public List<Community> listarRandomCommunities(int totalItems, Principal principal) {
        List<Community> comunidades = communityRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        if (principal != null) {
            String username = principal.getName();
            List<Community> userCommunities = userService.getUserCommunities(username);
            comunidades.removeAll(userCommunities);
        }

        if (comunidades.size() > totalItems) {
            comunidades = getRandomCommunities(comunidades, totalItems);
        }

        return comunidades;
    }

    public List<Community> getRandomCommunities(List<Community> comunidades, int totalItems) {
        Collections.shuffle(comunidades);
        return comunidades.subList(0, totalItems);
    }

    @Override
    public List<Community> listCommunitiesForUser(Usuario user) {
        log.error("listCommunitiesForUser [User ID: {}]", user.getId());
        return communityRepository
                .findCommunitiesByUserId(user.getId())
                .orElseThrow(() -> new CommunityNotFoundException(
                        "Communities not found for user", "No communities found for user with ID: " + user.getId()));
    }

    @Override
    public List<Community> listarCommunities(Sort sort) {
        return communityRepository.findAll(sort);
    }

    @Override
    public List<Community> listarCommunitiesPorUsuario(String idUsuario, String role) {
        return communityRepository
                .getCommunitiesByRole(role, idUsuario)
                .orElseThrow(() -> new CommunityNotFoundException(
                        "Community Not Found",
                        "No communities found for user with ID: " + idUsuario + " and role: " + role));
    }

    @Override
    public List<Community> buscarComunidades(String query) {
        return communityRepository
                .findByTitleContainingIgnoreCase(query)
                .orElseThrow(() ->
                        new CommunityNotFoundException("Community Not Found", "No communities with query: " + query));
    }

    @Override
    public Community pegarCommunity(String id) {
        return communityRepository
                .findById(id)
                .orElseThrow(() -> new CommunityNotFoundException(
                        "Community Not Found", "No communities found for user with ID: " + id));
    }

    @Override
    public String beginCommunityCreation(Model model, String idUsuario, Authentication authentication) {

        model = userUtils.setModelIfAuthenticationExists(authentication, model);
        model.addAttribute("community", new Community());
        model.addAttribute("idUsuario", idUsuario);

        return "/CriarComunidade";
    }

    @Override
    public String allMyCommunities(String idUsuario, Model model, Authentication authentication) {
        List<Community> adminCommunities = listarCommunitiesPorUsuario(idUsuario, "ADMIN");
        List<Community> moderatorCommunities = listarCommunitiesPorUsuario(idUsuario, "MODERATOR");
        List<Community> userCommunities = listarCommunitiesPorUsuario(idUsuario, "USER");

        model = userUtils.setModelIfAuthenticationExists(authentication, model);
        model.addAttribute("adminCommunities", adminCommunities);
        model.addAttribute("moderatorCommunities", moderatorCommunities);
        model.addAttribute("userCommunities", userCommunities);

        return "/MinhasComunidades";
    }

    @Override
    public ResponseEntity<String> changeCommunityIcon(
            String idComunidade, MultipartFile icone, HttpServletRequest request) {

        Community community = pegarCommunity(idComunidade);
        community.setIcone(communityUtils.createIconUrl(icone, request));
        atualizarComunidade(community);

        return ResponseEntity.ok("Success");
    }

    @Override
    public List<String> listCommunitiesWithOrWithoutSort(String query) {
        List<Community> communities;

        if (query != null && !query.isEmpty()) {
            communities = buscarComunidades(query);
        } else {
            communities = listarCommunities(Sort.by(Sort.Direction.ASC, "id"));
        }
        List<String> resultados = new ArrayList<>();
        for (Community community : communities) {
            resultados.add(community.getTitle());
        }

        return resultados;
    }

    @Override
    public UpdateCommunityRequest updateCommunity(
            String id, UpdateCommunityRequest updateCommunityRequest, BindingResult result) {
        Community community = pegarCommunity(updateCommunityRequest.getId());

        var communityToUpdate = communityMapper.fromUpdateRequestToCommunity(community, updateCommunityRequest);

        communityRepository.save(communityToUpdate);

        return updateCommunityRequest;
    }

    @Override
    public String prepareCommunityAndListOfPosts(String id, Model model, Authentication authentication) {

        model = userUtils.setModelIfAuthenticationExists(authentication, model);
        model.addAttribute("listPost", postUtils.listPostsByCommunity(id));
        model.addAttribute("community", pegarCommunity(id));
        return "/Comunidade";
    }

    @Override
    public String deleteCommunity(String idComunidade) {

        Community community = pegarCommunity(idComunidade);
        communityRepository.delete(community);

        return "redirect:/perfil";
    }

    @Override
    @Transactional
    public String salvarComunidade(
            Model model, BindingResult result, NovaComunidadeRequest novaComunidadeRequest, String idUsuario) {

        if (result.hasErrors()) {
            model.addAttribute("novaComunidadeRequest", novaComunidadeRequest);
            model.addAttribute("errors", result.getAllErrors());
            return "CriarComunidade";
        }
        var community = communityMapper.toCommunity(novaComunidadeRequest);

        List<Membros> membersToSave = memberMapper.toListOfMembers(
                userUtils.getUserById(idUsuario), roleCommunityService.findCommunityByUserRole("ADMIN"), community);

        community.setMembros(membersToSave);

        communityRepository.save(community);

        return "redirect:/perfil";
    }

    @Override
    public void atualizarComunidade(Community community) {
        communityRepository.save(community);
    }
}
