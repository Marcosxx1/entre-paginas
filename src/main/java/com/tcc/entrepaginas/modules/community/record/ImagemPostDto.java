package com.tcc.entrepaginas.modules.community.record;

import com.tcc.entrepaginas.domain.ImagemPost;

public record ImagemPostDto(String id, String nome, PostDto postDto) {

    public static ImagemPostDto fromPost(ImagemPost imagemPost) {
        return new ImagemPostDto(
                imagemPost.getId(),
                imagemPost.getNome(),
                PostDto.fromPost(imagemPost.getPost()));
    }

}
