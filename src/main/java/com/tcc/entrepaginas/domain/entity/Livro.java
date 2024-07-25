package com.tcc.entrepaginas.domain.entity;

import com.tcc.entrepaginas.domain.enums.Categoria;
import com.tcc.entrepaginas.domain.enums.Estado;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
import com.tcc.entrepaginas.domain.enums.Tipo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
public class Livro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Nome não deve estar em branco")
    private String nome;

    @NotBlank(message = "Descricao não deve estar em branco")
    private String descricao;

    @NotBlank(message = "Cidade não deve estar em branco")
    private String cidade;

    @NotNull(message = "Estado Brasil não deve ser nulo")
    private int estadoBrasil;

    @NotNull(message = "Estado não deve ser nulo")
    private int estado;

    @NotNull(message = "Tipo não deve ser nulo")
    private int tipo;

    @NotNull(message = "Categoria não deve ser nulo")
    private int categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "livro")
    private List<ImagemLivro> imagens;

    public Livro() {}

    public Livro(
            String id,
            String nome,
            String descricao,
            String cidade,
            int estadoBrasil,
            int estado,
            int tipo,
            int categoria,
            Usuario usuario,
            List<ImagemLivro> imagens) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.cidade = cidade;
        this.estadoBrasil = estadoBrasil;
        this.estado = estado;
        this.tipo = tipo;
        this.categoria = categoria;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public EstadoBrasil getEstadoBrasil() {
        return EstadoBrasil.valueOf(estadoBrasil);
    }

    public void setEstadoBrasil(EstadoBrasil estadoBrasil) {
        if (estadoBrasil != null) {
            this.estadoBrasil = estadoBrasil.getCode();
        }
    }
}
