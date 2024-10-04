package com.tcc.entrepaginas.service.community;

import com.tcc.entrepaginas.domain.dto.NovaComunidadeRequest;
import com.tcc.entrepaginas.domain.dto.SearchBarResponse;
import com.tcc.entrepaginas.domain.dto.UpdateCommunityRequest;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Membros;
import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.CommunityNotFoundException;
import com.tcc.entrepaginas.mapper.community.CommunityMapper;
import com.tcc.entrepaginas.mapper.member.MemberMapper;
import com.tcc.entrepaginas.repository.CommunityRepository;
import com.tcc.entrepaginas.repository.MembrosRepository;
import com.tcc.entrepaginas.repository.ReactionRepository;
import com.tcc.entrepaginas.service.rolecomunity.RoleCommunityService;
import com.tcc.entrepaginas.service.user.UserService;
import com.tcc.entrepaginas.utils.PostUtils;
import com.tcc.entrepaginas.utils.community.CommunityUtils;
import com.tcc.entrepaginas.utils.user.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityServiceImplNew implements CommunityServiceNew {

    private final RoleCommunityService roleCommunityService;
    private final CommunityRepository communityRepository;
    private final ReactionRepository reactionRepository;
    private final CommunityMapper communityMapper;
    private final MembrosRepository memberRepository;
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
                .orElseThrow(() -> new CommunityNotFoundException("Community Not Found",
                        "No communities with query: " + query));
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
    public String changeCommunityIcon(String idComunidade, MultipartFile icone, HttpServletRequest request) {

        Community community = pegarCommunity(idComunidade);
        community.setIcone(communityUtils.createIconUrl(icone, request));
        atualizarComunidade(community);

        return "redirect:/community/" + community.getId();
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
    public List<SearchBarResponse> searchBar(String query) {
        List<Community> communities;

        if (query != null && !query.isEmpty()) {
            communities = buscarComunidades(query);
        } else {
            communities = listarCommunities(Sort.by(Sort.Direction.ASC, "id"));
        }

        List<SearchBarResponse> resultados = new ArrayList<>();
        for (Community community : communities) {
            resultados.add(new SearchBarResponse(community.getId(), community.getTitle()));
        }

        return resultados;
    }

    @Override
    @Transactional
    public String updateCommunity(String id, UpdateCommunityRequest updateCommunityRequest, BindingResult result) {
        Community community = pegarCommunity(id);

        var communityToUpdate = communityMapper.fromUpdateRequestToCommunity(community, updateCommunityRequest);

        communityRepository.save(communityToUpdate);

        return "redirect:/community/" + community.getId();
    }

    @Override
    public String prepareCommunityAndListOfPosts(String id, Model model, Authentication authentication) {

        var comunidade = pegarCommunity(id);

        UpdateCommunityRequest updateCommunityRequest = UpdateCommunityRequest.builder()
                .title(comunidade.getTitle())
                .content(comunidade.getContent())
                .build();

        model = userUtils.setModelIfAuthenticationExists(authentication, model);
        model.addAttribute("listPost", postUtils.listPostsByCommunity(id));
        model.addAttribute("community", pegarCommunity(id));
        model.addAttribute("updateCommunityRequest", updateCommunityRequest);
        var isMember = isUserMember(userUtils.getIdUserFromUserDetail(authentication), id);
        model.addAttribute("isMember", isMember.isPresent());

        if (isMember.isPresent()) {
            model.addAttribute("membro", isMember.get());
        }

        Map<String, Integer> reaction = new HashMap<>();
        for (Post post : comunidade.getPost()) {
            reaction.put(post.getId(), reactionRepository.countByReacao(post.getId(), "like"));
        }

        model.addAttribute("qtdReaction", reaction);

        return "/Comunidade";
    }

    public Optional<Membros> isUserMember(String userId, String communityId) {
        return memberRepository.findByCommunityAndUsuario(userId, communityId);
    }

    @Override
    public String deleteCommunity(String idComunidade) {

        Community community = pegarCommunity(idComunidade);
        communityRepository.delete(community);

        return "redirect:/perfil";
    }

    @Override
    public String deleteCommunityAdmin(String idComunidade) {

        Community community = pegarCommunity(idComunidade);
        communityRepository.delete(community);

        return "redirect:/admin";
    }

    @Override
    @Transactional
    public String deleteMemberFromCommunity(String communityId, String memberId) {
        communityRepository.deleteMembroByCommunityIdAndMemberId(communityId, memberId);
        return "redirect:/community/" + communityId;
    }

    @Override
    @Transactional
    public String salvarComunidade(
            Model model,
            BindingResult result,
            NovaComunidadeRequest novaComunidadeRequest,
            String idUsuario,
            MultipartFile image,
            HttpServletRequest request) {

        if (result.hasErrors()) {
            model.addAttribute("novaComunidadeRequest", novaComunidadeRequest);
            model.addAttribute("errors", result.getAllErrors());
            return "CriarComunidade";
        }

        var community = communityMapper.toCommunity(novaComunidadeRequest);

        List<Membros> membersToSave = memberMapper.toListOfMembers(
                userUtils.getUserById(idUsuario), roleCommunityService.findCommunityByUserRole("ADMIN"), community);

        community.setMembros(membersToSave);
        community.setIcone(communityUtils.createIconUrl(image, request));

        communityRepository.save(community);

        return "redirect:/community/mycommunities/" + idUsuario;
    }

    @Override
    public void atualizarComunidade(Community community) {
        communityRepository.save(community);
    }
}
