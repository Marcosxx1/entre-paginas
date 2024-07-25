package com.tcc.entrepaginas.domain.enums;

public enum Tipo {
    Capa_Dura(0),
    Brochura(1),
    Livro_de_Bolso(2);

    private int code;

    private Tipo(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static Tipo valueOf(int code) {
        for (Tipo value : Tipo.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Tipo code");
    }
}
