package com.tcc.entrepaginas.modules.community.usecases.posts.updatepost;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.community.entities.Community;
import com.tcc.entrepaginas.modules.community.entities.Post;
import com.tcc.entrepaginas.modules.community.record.PostDto;
import com.tcc.entrepaginas.modules.community.repositories.CommunityRepository;
import com.tcc.entrepaginas.modules.community.repositories.ImagemPostRepository;
import com.tcc.entrepaginas.modules.community.repositories.PostRepository;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UpdatePostUseCase {

    @Value("${image.path}")
    private static String imagePath;

    private static String imagePathUser = imagePath + "/post/";
    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final CommunityRepository communityRepository;

    @Autowired
    private final ImagemPostRepository imagemPostRepository;

    public UpdatePostUseCase(PostRepository postRepository, UsuarioRepository usuarioRepository,
            CommunityRepository communityRepository, ImagemPostRepository imagemPostRepository) {
        this.communityRepository = communityRepository;
        this.usuarioRepository = usuarioRepository;
        this.postRepository = postRepository;
        this.imagemPostRepository = imagemPostRepository;
    }

    public PostDto updatePost(String postDtoJson, String usuarioId, String communityId,
            MultipartFile image) {

        ObjectMapper objectMapper = new ObjectMapper();
        PostDto postDto = null;
        try {
            postDto = objectMapper.readValue(postDtoJson, PostDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (postDto == null) {
            throw new RuntimeException("Falha ao converter JSON para PostDto");
        }

        PostDto createdPost = null;

        try {
            Post post = this.pegarPost(postDto.id());

            post.setTitle(postDto.title());
            post.setContent(postDto.content());

            postRepository.save(post);

            createdPost = PostDto.fromPost(post);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound(postDto.id());
        }

        return createdPost;
    }

    public Community pegarComunidade(String id) {
        Optional<Community> comm = communityRepository.findById(id);
        return comm.orElseThrow(() -> new ResourceNotFound(id));
    }

    public Usuario pegarUsuario(String id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ResourceNotFound(id));
    }

    public Post pegarPost(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new ResourceNotFound(id));
    }

}
