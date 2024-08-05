package com.tcc.entrepaginas.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class NovaComunidadeRequest {

    @NotBlank(message = "Título não pode estar em branco")
    @Size(max = 255, message = "Cidade não pode ter mais de 255 caracteres")
    private String title;

    @NotBlank(message = "Conteúdo não pode estar em branco")
    @Size(max = 500, message = "Conteúdo não pode ter mais de 500 caracteres")
    private String content;

    private Boolean privado;
}
