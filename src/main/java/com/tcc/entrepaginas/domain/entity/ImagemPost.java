package com.tcc.entrepaginas.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class ImagemPost extends Imagem implements Serializable {

    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    public ImagemPost() {}

    public ImagemPost(String nome, Post post) {
        super(nome);
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
