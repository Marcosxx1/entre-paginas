package com.tcc.entrepaginas.modules.community.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Community implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String icone;

    private Boolean privado;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Membros> membros;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant date;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> post;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = Instant.now();
        }
    }

    public Community() {
    }

    public Community(@NotBlank String title, @NotBlank String content, Boolean privado,
            Instant date) {
        this.title = title;
        this.content = content;
        this.privado = privado;
        this.date = date;
    }

    public Community(String id, @NotBlank String title, @NotBlank String content, String icone, Boolean privado,
            Instant date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.icone = icone;
        this.privado = privado;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public Boolean getPrivado() {
        return privado;
    }

    public void setPrivado(Boolean privado) {
        this.privado = privado;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public List<Membros> getMembros() {
        return membros;
    }

    public void setMembros(List<Membros> membros) {
        this.membros = membros;
    }

}
