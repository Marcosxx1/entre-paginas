package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Post;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface PostServiceNew {
    List<Post> listarPost(Sort sort);

    List<Post> listAllPostsInACommunity(String username);

    List<Post> listPostsByCommunity(String communityId);
}
