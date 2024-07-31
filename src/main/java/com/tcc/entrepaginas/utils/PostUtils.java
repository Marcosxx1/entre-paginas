package com.tcc.entrepaginas.utils;

import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostUtils {

    private final PostRepository postRepository;

    public List<Post> listPostsByCommunity(String communityId) {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "date")).stream()
                .filter(post -> post.getCommunity().getId().equals(communityId))
                .collect(Collectors.toList());
    }
}
