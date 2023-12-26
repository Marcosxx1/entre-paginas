package com.tcc.entrepaginas.modules.books.entities;

import java.io.Serializable;
import java.util.List;

import com.tcc.entrepaginas.modules.books.entities.enums.Categoria;
import com.tcc.entrepaginas.modules.books.entities.enums.Estado;
import com.tcc.entrepaginas.modules.books.entities.enums.Tipo;
import com.tcc.entrepaginas.modules.users.entities.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Livro implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    private String descricao;

    private String editora;

    private int estado;

    private int tipo;

    private int categoria;

    private Double preco;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "livro")
    private List<ImagemLivro> imagens;

    // @ManyToMany(fetch = FetchType.EAGER)
    // @JoinTable(name = "livro_autor", joinColumns = @JoinColumn(name =
    // "livro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
    // private List<Autor> autores;

    public Livro() {
    }

    public Livro(String nome, String descricao, String editora, int estado, int tipo,
            int categoria, int status, Double preco, Usuario usuario, List<ImagemLivro> imagens) {
        this.nome = nome;
        this.descricao = descricao;
        this.editora = editora;
        this.estado = estado;
        this.tipo = tipo;
        this.categoria = categoria;
        this.preco = preco;
        this.usuario = usuario;
        this.imagens = imagens;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Estado getEstado() {
        return Estado.valueOf(estado);
    }

    public void setEstado(Estado estado) {
        if (estado != null) {
            this.estado = estado.getCode();
        }
    }

    public Tipo getTipo() {
        return Tipo.valueOf(tipo);
    }

    public void setTipo(Tipo tipo) {
        if (tipo != null) {
            this.tipo = tipo.getCode();
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ImagemLivro> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemLivro> imagens) {
        this.imagens = imagens;
    }

    public Categoria getCategoria() {
        return Categoria.valueOf(categoria);
    }

    public void setCategoria(Categoria categoria) {
        if (categoria != null) {
            this.categoria = categoria.getCode();
        }
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }
}
