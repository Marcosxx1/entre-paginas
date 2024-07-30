package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovaComunidadeRequest;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.CustomException;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.mapper.CommunityMapper;
import com.tcc.entrepaginas.mapper.member.MemberMapper;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import com.tcc.entrepaginas.repository.CommunityRepository;
import com.tcc.entrepaginas.repository.MembrosRepository;
import com.tcc.entrepaginas.utils.user.UserUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityServiceImplNew implements CommunityServiceNew {

    private final CommunityRepository communityRepository;
    private final UsuarioService usuarioService;
    private final RoleCommunityService roleCommunityService;
    private final MembrosRepository membrosRepository;
    private final MemberMapper memberMapper;
    private final CommunityMapper communityMapper;
    private final UserUtils userUtils;

    private Path root = Paths.get("icone");

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

        return "/MinhasComunidades";    }

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
        membrosRepository.save(toSave);// TODO 2/2 - MOVER PARA MemberService

        return "redirect:/perfil";
    }

    @Override
    public void atualizarComunidade(Community community) {
        Objects.requireNonNull(community, "Community must not be null"); // TODO - VERIFICAR
        communityRepository.save(community);
    }

    @Override
    public void apagarComunidadePorId(String id) {
        Objects.requireNonNull(id, "ID must not be null"); // TODO - VERIFICAR

        Community community = pegarCommunity(id);
        communityRepository.delete(community);
    }

    @Override
    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new CustomException("Could not initialize folder for upload!");
        }
    }

    @Override
    public String atualizarIconeComunidade(MultipartFile image) {
        try {
            init();

            UUID uuid = UUID.randomUUID();

            String newFileName = uuid.toString();

            String extension = FilenameUtils.getExtension(image.getOriginalFilename());

            String fileName = newFileName + "." + extension;

            Files.copy(image.getInputStream(), this.root.resolve(fileName));

            return fileName;
        } catch (Exception e) {
            throw new CustomException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
