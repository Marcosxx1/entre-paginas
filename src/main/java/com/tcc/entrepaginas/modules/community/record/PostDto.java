package com.tcc.entrepaginas.modules.community.record;

import com.tcc.entrepaginas.domain.entity.ImagemPost;
import com.tcc.entrepaginas.domain.entity.Post;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public record PostDto(
        String id, String title, String content, ImagemPost Image, Instant date, List<CommentsDto> comments) {

    public static PostDto fromPost(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImage(),
                post.getDate(),
                CommentsDto.transformeCommentsEmDto(
                        post.getComments() != null ? post.getComments() : new ArrayList<>()));
    }

    public static List<PostDto> transformePostEmDto(List<Post> posts) {
        List<PostDto> postDtos = new ArrayList<>();

        for (Post post : posts) {
            PostDto postDto = PostDto.fromPost(post);
            postDtos.add(postDto);
        }

        return postDtos;
    }
}
