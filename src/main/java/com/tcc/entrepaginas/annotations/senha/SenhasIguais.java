package com.tcc.entrepaginas.annotations.senha;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SenhasIguaisValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SenhasIguais {
    String message() default "As senhas não são iguais";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
