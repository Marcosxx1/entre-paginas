package com.tcc.entrepaginas.modules.books.entities.enums;

public enum EstadoBrasil {
    AC(1),
    AL(2),
    AP(3),
    AM(4),
    BA(5),
    CE(6),
    DF(7),
    ES(8),
    GO(9),
    MA(10),
    MT(11),
    MS(12),
    MG(13),
    PA(14),
    PB(15),
    PR(16),
    PE(17),
    PI(18),
    RJ(19),
    RN(20),
    RS(21),
    RO(22),
    RR(23),
    SC(24),
    SP(25),
    SE(26),
    TO(27);

    private int code;

    private EstadoBrasil(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static EstadoBrasil valueOf(int code) {
        for (EstadoBrasil value : EstadoBrasil.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid EstadoBrasil code");
    }
}
