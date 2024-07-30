package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.ImagemPost;
import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.CustomException;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import com.tcc.entrepaginas.service.CommunityServiceNew;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class PostService {

    private final Path root = Paths.get("post");

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommunityServiceNew communityServiceOld;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ImagemPostRepository imagemPostRepository;

    public Post buscarPost(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new ResourceNotFound(id));
    }

    public List<Post> listarPost(Sort sort) {
        return postRepository.findAll(sort);
    }

    public void apagarPostPorId(String id) {
        Post post = this.buscarPost(id);
        postRepository.delete(post);
    }

    public void createPost(
            String communityId, String userId, MultipartFile image, Post post, HttpServletRequest request) {

        Usuario usuario = usuarioService.pegarUsuario(userId);
        System.out.println(usuario);
        Community community = communityServiceOld.pegarCommunity(communityId);

        post.setUsuario(usuario);
        post.setCommunity(community);

        postRepository.save(post);

        if (image != null && !image.isEmpty()) {
            // O conteúdo da imagem está presente
            String fileName = this.atualizarImagemPost(image);
            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();
            String url = baseUrl + "/post/" + fileName;

            ImagemPost imagePost = new ImagemPost(url, post);

            imagemPostRepository.save(imagePost);
        }
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

    public String atualizarImagemPost(MultipartFile image) {
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
