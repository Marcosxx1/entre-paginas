package com.tcc.entrepaginas.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImagemLivro extends Imagem {

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    private String url;

    private long tamanhoEmBytes;

    public ImagemLivro(String url, Livro livro) {
        super();
        this.livro = livro;
        this.url = url;
    }
}
