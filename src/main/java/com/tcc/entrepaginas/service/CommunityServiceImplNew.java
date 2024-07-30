package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovaComunidadeRequest;
import com.tcc.entrepaginas.domain.dto.UpdateCommunityRequest;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.mapper.community.CommunityMapper;
import com.tcc.entrepaginas.mapper.member.MemberMapper;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import com.tcc.entrepaginas.repository.CommunityRepository;
import com.tcc.entrepaginas.repository.MembrosRepository;
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
    private final MembrosRepository membrosRepository;
    private final CommunityMapper communityMapper;
    private final PostServiceNew postServiceNew;
    private final CommunityUtils communityUtils;
    private final UsuarioService usuarioService;
    private final MemberMapper memberMapper;
    private final UserUtils userUtils;

    public List<Community> listarRandomCommunities(int totalItems, Principal principal) {
        List<Community> comunidades = communityRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        if (principal != null) {
            String username = principal.getName();
            List<Community> userCommunities = usuarioService.getUserCommunities(username);
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
        log.error("listCommunitiesForUser [{}]", user.getId());
        return communityRepository
                .findCommunitiesByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFound(user.getId()));
    }

    @Override
    public List<Community> listarCommunities(Sort sort) {
        Objects.requireNonNull(sort, "Sort must not be null"); // TODO - FAZER VERIFICAÇÃO UMA CAMADA ACIMA
        return communityRepository.findAll(sort);
    }

    @Override
    public List<Community> listarCommunitiesPorUsuario(String idUsuario, String role) {

        return communityRepository.getCommunitiesByRole(role, idUsuario); // TODO - VERIFICAR SE REALMENTE EXISTE
    }

    @Override
    public List<Community> buscarComunidades(String query) { // TODO - VERIFICAR SE EXISTE
        return communityRepository.findByTitleContainingIgnoreCase(query);
    }

    @Override
    public Community pegarCommunity(String id) {
        Objects.requireNonNull(id, "ID must not be null"); // TODO - VERIFICAR UMA CAMADA ACIMA

        Optional<Community> community = communityRepository.findById(id);
        return community.orElseThrow(() -> new ResourceNotFound(id));
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
        // TODO - Eu tenho a impressão que poderíamos rever esse método
        // Pensemos, temos o listar comunidades e esse agora, a única mudança é a String query
        // Talvez poderíamos ter um método só, que o default da query é "", ou seja, se não for passado ele busca normal
        // Se for, busca com a query
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
        model.addAttribute("listPost", postServiceNew.listPostsByCommunity(id));
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
        var toSave = memberMapper.toMember( // TODO 1/2 - MOVER PARA MemberService
                usuarioService.pegarUsuario(idUsuario), // PEGAR DO CONTEXTO
                roleCommunityService.findCommunityByUserRole("ADMIN"),
                community);

        communityRepository.save(community); // Talvez não precise, como Membros tem relacionamento com as entidades,
        /*Eu creio que podemos fazer algo como
         * community.setMembros.... e tal
         * Dessa forma não precisaremos chamar dois saves (Fica mais fácil de entender como está agora)
         * Mas se podemos fazer tudo de uma vez, porque não?*/
        membrosRepository.save(toSave); // TODO 2/2 - MOVER PARA MemberService

        return "redirect:/perfil";
    }

    @Override
    public void atualizarComunidade(Community community) {
        Objects.requireNonNull(community, "Community must not be null"); // TODO - VERIFICAR
        communityRepository.save(community);
    }
}
