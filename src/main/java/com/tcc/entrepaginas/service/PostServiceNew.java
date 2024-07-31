package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoPostRequest;
import com.tcc.entrepaginas.domain.entity.Post;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

public interface PostServiceNew {
    List<Post> listarPost(Sort sort);

    List<Post> listAllPostsInACommunity(String username);

    // List<Post> listPostsByCommunity(String communityId);

    Post buscarPost(String id);

    String apagarPostPorId(String id);

    String createPost(
            String communityId,
            String userId,
            MultipartFile image,
            NovoPostRequest novoPostRequest,
            HttpServletRequest request);
}
