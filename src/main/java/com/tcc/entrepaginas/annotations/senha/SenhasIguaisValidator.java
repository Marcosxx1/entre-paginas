package com.tcc.entrepaginas.annotations.senha;

import com.tcc.entrepaginas.domain.dto.NovoUsuarioRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SenhasIguaisValidator implements ConstraintValidator<SenhasIguais, NovoUsuarioRequest> {

    @Override
    public void initialize(SenhasIguais constraintAnnotation) {}

    @Override
    public boolean isValid(NovoUsuarioRequest request, ConstraintValidatorContext context) {
        return request.getSenha().equals(request.getConfirmarSenha());
    }
}
