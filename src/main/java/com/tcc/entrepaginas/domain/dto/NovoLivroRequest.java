package com.tcc.entrepaginas.domain.dto;

import com.tcc.entrepaginas.domain.enums.Categoria;
import com.tcc.entrepaginas.domain.enums.Estado;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
import com.tcc.entrepaginas.domain.enums.Tipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class NovoLivroRequest {

    @NotBlank(message = "Nome não pode estar em branco")
    // @Size(max = 255, message = "Nome não pode ter mais de 255 caracteres")
    private String nome;

    @NotBlank(message = "Descrição não pode estar em branco")
    // @Size(max = 500, message = "Descrição não pode ter mais de 500 caracteres")
    private String descricao;

    @NotBlank(message = "Cidade não pode estar em branco")
    // @Size(max = 255, message = "Cidade não pode ter mais de 255 caracteres")
    private String cidade;

    @NotNull(message = "Estado do Brasil não pode ser nulo")
    private EstadoBrasil estadoBrasil;

    @NotNull(message = "Estado não pode ser nulo")
    private Estado estado;

    @NotNull(message = "Tipo não pode ser nulo")
    private Tipo tipo;

    @NotNull(message = "Categoria não pode ser nula")
    private Categoria categoria;

    // @NotNull(message = "imagens não pode ser nula")
    // private List<MultipartFile> imagens;
}
