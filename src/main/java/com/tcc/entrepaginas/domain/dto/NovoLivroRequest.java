package com.tcc.entrepaginas.domain.dto;

import com.tcc.entrepaginas.domain.enums.Categoria;
import com.tcc.entrepaginas.domain.enums.Estado;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
import com.tcc.entrepaginas.domain.enums.Tipo;
import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class NovoLivroRequest {
    private String nome;

    private String descricao;

    private String cidade;

    private EstadoBrasil estadoBrasil;

    private Estado estado;

    private Tipo tipo;

    private Categoria categoria;
}
