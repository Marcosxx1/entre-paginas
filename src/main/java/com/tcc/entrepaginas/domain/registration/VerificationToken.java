package com.tcc.entrepaginas.domain.registration;

import com.tcc.entrepaginas.domain.entity.Usuario;
import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String token;

    private Date expirationTime;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    public VerificationToken(String token, Usuario usuario) {
        this.token = token;
        this.usuario = usuario;
        this.expirationTime = TokenExpirationTime.getExpirationTime();
    }
}
