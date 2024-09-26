package com.tcc.entrepaginas.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class UpdateUserNameLoginAndEmailRequest {

    private String id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome não pode ter mais de 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email não pode ter mais de 100 caracteres")
    private String email;

    @NotBlank(message = "Login é obrigatório")
    @Size(max = 50, message = "Login não pode ter mais de 50 caracteres")
    private String login;

    private String cidade;

    private String estadoBrasil;
}
