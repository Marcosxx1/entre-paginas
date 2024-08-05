package com.tcc.entrepaginas.domain.registration;

import com.tcc.entrepaginas.domain.entity.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private Usuario usuario;
    private String confirmationUrl;

    public RegistrationCompleteEvent(Usuario usuario, String confirmationUrl) {
        super(usuario);
        this.usuario = usuario;
        this.confirmationUrl = confirmationUrl;
    }
}
