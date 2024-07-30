package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoPostRequest;
import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.ImagemPost;
import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.mapper.post.PostMapper;
import com.tcc.entrepaginas.repository.ImagemPostRepository;
import com.tcc.entrepaginas.repository.PostRepository;
import com.tcc.entrepaginas.utils.imageupload.ImageUtils;
import com.tcc.entrepaginas.utils.user.UserUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostServiceNewImpl implements PostServiceNew {

    private final PostRepository postRepository;
    private final ImagemPostRepository imagemPostRepository;

    private final UserService userService;
    private final PostMapper postMapper;
    private final UserUtils userUtils;

    private static final Path ROOT = Paths.get("post");

    @PostConstruct
    public void init() {
        ImageUtils.init(ROOT);
    } // Isso é normal, pode dizer que não está sendo utilizado. Mas está, quando o Spring inicia ele seta para a gente antes de tudo.

    @Override
    public String createPost(
            String communityId,
            String userId,
            MultipartFile image,
            NovoPostRequest novoPostRequest,
            HttpServletRequest request) {

        Usuario usuario = userUtils.getUserById(userId);
        Community community = postRepository
                .findCommunityById(communityId)
                .orElseThrow(() -> new ResourceNotFound("Community not found with id: " + communityId));

        var post = postMapper.toPostFromNewRequest(novoPostRequest);

        post.setUsuario(usuario);
        post.setCommunity(community);

        postRepository.save(post);

        ImagemPost imagePost = ImageUtils.createImagePost(image, request, post, ROOT);//TODO - REVER FLUXO
        if (imagePost != null) {
            imagemPostRepository.save(imagePost);
        }

        return "redirect:/community/" + communityId;
    }

    @Override
    public List<Post> listarPost(Sort sort) {
        return postRepository.findAll(sort);
    }

    @Override
    public List<Post> listAllPostsInACommunity(String username) {
        List<Community> userCommunities = userService.getUserCommunities(username);

        return userCommunities.stream()
                .flatMap(community -> postRepository.findByCommunity(community).stream())
                .sorted(Comparator.comparing(Post::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> listPostsByCommunity(String communityId) {
        return listarPost(Sort.by(Sort.Direction.DESC, "date")).stream()
                .filter(post -> post.getCommunity().getId().equals(communityId))
                .collect(Collectors.toList());
    }

    @Override
    public Post buscarPost(String id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFound(id));
    }

    @Override
    public void apagarPostPorId(String id) {
        Post post = buscarPost(id);
        postRepository.delete(post);
    }


}
