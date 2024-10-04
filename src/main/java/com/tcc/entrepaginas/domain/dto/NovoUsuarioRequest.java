package com.tcc.entrepaginas.domain.dto;

import com.tcc.entrepaginas.annotations.senha.SenhasIguais;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@SenhasIguais // Anotação personalizada
public class NovoUsuarioRequest {

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

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
    private String senha;

    @NotBlank(message = "Confirmação de senha é obrigatória")
    @Size(min = 8, message = "Confirmação de senha deve ter pelo menos 8 caracteres")
    private String confirmarSenha;

    private String estadoBrasil;

    private String cidade;
}
