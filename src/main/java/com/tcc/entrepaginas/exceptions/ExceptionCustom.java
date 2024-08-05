package com.tcc.entrepaginas.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ExceptionCustom extends RuntimeException {

    private String title;
    private String detail;

    public ExceptionCustom(String title, String detail) {
        super(detail);
        this.title = title;
        this.detail = detail;
    }
}
