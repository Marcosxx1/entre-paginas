package com.tcc.entrepaginas.modules.community.usecases.posts.votespost;

import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.community.record.PostDto;
import com.tcc.entrepaginas.repository.PostRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotesPostUseCase {

    @Autowired
    private PostRepository postRepository;

    public VotesPostUseCase(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostDto votesPost(String id) {
        Post post = this.pegarPost(id);

        return PostDto.fromPost(post);
    }

    public Post pegarPost(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new ResourceNotFound(id));
    }
}
