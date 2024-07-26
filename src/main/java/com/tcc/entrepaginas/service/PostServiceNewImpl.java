package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import com.tcc.entrepaginas.repository.PostRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostServiceNewImpl implements PostServiceNew {

    private final PostRepository postRepository;
    private final UsuarioService usuarioService;

    @Override
    public List<Post> listarPost(Sort sort) {
        return postRepository.findAll(sort);
    }

    @Override
    public List<Post> listAllPostsInACommunity(String username) {
        List<Community> userCommunities = usuarioService.getUserCommunities(username);

        return userCommunities.stream()
                .flatMap(community -> postRepository.findByCommunity(community).stream())
                .sorted(Comparator.comparing(Post::getDate).reversed())
                .collect(Collectors.toList());
    }
}
