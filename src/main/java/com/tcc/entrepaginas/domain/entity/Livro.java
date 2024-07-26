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
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    private String descricao;

    private String cidade;

    private EstadoBrasil estadoBrasil;

    private Estado estado;

    private Tipo tipo;

    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "livro")
    private List<ImagemLivro> imagens;

}
