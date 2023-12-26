package com.tcc.entrepaginas.modules.books.entities;

import java.io.Serializable;

import com.tcc.entrepaginas.modules.heranca.Imagem;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ImagemLivro extends Imagem{

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

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public long getTamanhoEmBytes() {
        return tamanhoEmBytes;
    }

    public void setTamanhoEmBytes(long tamanhoEmBytes) {
        this.tamanhoEmBytes = tamanhoEmBytes;
    }

}
