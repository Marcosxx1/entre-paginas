package com.tcc.entrepaginas.domain.enums;

public enum Estado {
    Excelente(1),
    Bom(2),
    Ruim(3),
    Pessimo(4);

    private int code;

    private Estado(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static Estado valueOf(int code) {
        for (Estado value : Estado.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Estado code");
    }
}
