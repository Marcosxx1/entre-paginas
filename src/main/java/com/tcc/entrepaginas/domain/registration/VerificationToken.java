package com.tcc.entrepaginas.domain.registration;

import com.tcc.entrepaginas.domain.entity.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;

    private String token;

    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public VerificationToken( String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
        this.expirationTime = TokenExpirationTime.getExpirationTime();
    }
}
