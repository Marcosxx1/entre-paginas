package com.tcc.entrepaginas.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@Getter
@Setter
public class ImagemLivro extends Imagem {

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    private long tamanhoEmBytes;

    public ImagemLivro() {
        super();
    }

    public ImagemLivro(String nome, Livro livro) {
        super(nome);
        this.livro = livro;
    }

    }
