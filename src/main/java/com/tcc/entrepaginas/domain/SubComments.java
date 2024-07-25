package com.tcc.entrepaginas.domain;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;

@Entity
public class SubComments implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    @Column(columnDefinition = "TEXT")   
    @NotBlank(message = "Conteúdo não pode estar em branco")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant date;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = Instant.now();
        }
    }

    @ManyToOne
    @JoinColumn(name = "comments_id")
    private Comments parentComment;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public SubComments(@NotBlank String content, Comments parentComment, Usuario usuario) {
        this.content = content;
        this.parentComment = parentComment;
        this.usuario = usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Comments getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comments parentComment) {
        this.parentComment = parentComment;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
