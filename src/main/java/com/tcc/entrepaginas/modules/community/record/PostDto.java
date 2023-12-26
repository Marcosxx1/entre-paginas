package com.tcc.entrepaginas.modules.community.record;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.tcc.entrepaginas.modules.community.entities.ImagemPost;
import com.tcc.entrepaginas.modules.community.entities.Post;

public record PostDto(String id, String title, String content, ImagemPost Image, int likes,
        Instant date, List<CommentsDto> comments) {

    public static PostDto fromPost(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImage(),
                post.getLikes(),
                post.getDate(),
                CommentsDto.transformeCommentsEmDto(post.getComments() != null ? post.getComments()
                        : new ArrayList<>()));
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
