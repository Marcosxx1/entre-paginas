package com.tcc.entrepaginas.domain;

import java.io.Serializable;

import com.tcc.entrepaginas.domain.enums.ReacaoStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity()
public class Reaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String reacao;

    @Enumerated(EnumType.STRING)
    private ReacaoStatus jaVotou;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Reaction() {
    }

    public Reaction(String reacao, Usuario usuario, Post post) {
        this.reacao = reacao;
        this.usuario = usuario;
        this.post = post;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReacao() {
        return reacao;
    }

    public void setReacao(String reacao) {
        this.reacao = reacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public ReacaoStatus getJaVotou() {
        return jaVotou;
    }

    public void setJaVotou(ReacaoStatus jaVotou) {
        this.jaVotou = jaVotou;
    }

    @Override
    public String toString() {
        return "Reaction [id=" + id + ", reacao=" + reacao + ", usuario=" + usuario + ", post=" + post + ", getId()="
                + getId() + ", getReacao()=" + getReacao() + ", getUsuario()=" + getUsuario() + ", getPost()="
                + getPost() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
                + super.toString() + "]";
    }

}