package com.tcc.entrepaginas.mapper.post;

import com.tcc.entrepaginas.domain.dto.NovoPostRequest;
import com.tcc.entrepaginas.domain.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toPostFromNewRequest(NovoPostRequest novoPostRequest) {
        return Post.builder()
                .title(novoPostRequest.getTitle())
                .content(novoPostRequest.getContent())
                .build();
    }
}
