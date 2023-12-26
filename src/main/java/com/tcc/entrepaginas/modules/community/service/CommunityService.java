package com.tcc.entrepaginas.modules.community.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcc.entrepaginas.exceptions.CustomException;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.community.entities.Community;
import com.tcc.entrepaginas.modules.community.entities.Membros;
import com.tcc.entrepaginas.modules.community.repositories.CommunityRepository;
import com.tcc.entrepaginas.modules.community.repositories.MembrosRepository;
import com.tcc.entrepaginas.modules.community.repositories.RoleCommunityRepository;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;

@Service
public class CommunityService {

    private final Path root = Paths.get("icone");

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleCommunityRepository roleCommunityRepository;

    @Autowired
    private MembrosRepository membrosRepository;

    public Community pegarCommunity(String id) {
        Optional<Community> community = communityRepository.findById(id);
        return community.orElseThrow(() -> new ResourceNotFound(id));
    }

    public List<Community> listarCommunities(Sort sort) {
        return communityRepository.findAll(sort);
    }

    public List<Community> listarCommunitiesPorUsuario(String idUsuario, String role) {
        return communityRepository.getCommunitiesByRole(role, idUsuario);
    }

    public void salvarComunidade(Community community, String idUsuario) {

        Membros adicionarMembro = new Membros(
                usuarioService.pegarUsuario(idUsuario),
                roleCommunityRepository.findByPapel("ADMIN"),
                community);

        communityRepository.save(community);
        membrosRepository.save(adicionarMembro);
    }

    public void atualizarComunidade(Community community) {
        communityRepository.save(community);
    }

    public void apagarComunidadePorId(String id) {
        Community community = this.pegarCommunity(id);
        communityRepository.delete(community);
    }

    public List<Community> listarRandomCommunities(int totalItems, Principal principal) {
        List<Community> comunidades = this.listarCommunities(Sort.by(Sort.Direction.ASC, "id"));

        if (principal != null) {
            String username = principal.getName();
            List<Community> userCommunities = usuarioService.getUserCommunities(username);
            comunidades.removeAll(userCommunities);
        }

        if (comunidades.size() > totalItems) {
            comunidades = this.getRandomCommunities(comunidades, totalItems);
        }

        return comunidades;
    }

    public List<Community> getRandomCommunities(List<Community> comunidades, int totalItems) {
        Collections.shuffle(comunidades);
        return comunidades.subList(0, totalItems);
    }

    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new CustomException("Could not initialize folder for upload!");
        }
    }

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
