package com.tcc.entrepaginas.domain.enums;

public enum Categoria {
    Ação(1),
    Arte(2),
    Autoajuda(3),
    Biografia(4),
    Ciências_Sociais(5),
    Conto(6),
    Crimes(7),
    Ensaios(8),
    Família(9),
    Fantasia(10),
    Ficção(11),
    Gastronomia(12),
    Graphic_Novel(13),
    Guias(14),
    História(15),
    Horror(16),
    Humor(17),
    Infantil(18),
    Novela(19),
    Realismo(20),
    Religião(21),
    Romance(22),
    Tecnologia(23),
    Thriller(24),
    Viagem(25);

    private int code;

    private Categoria(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static Categoria valueOf(int code) {
        for (Categoria value : Categoria.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid Categoria code");
    }

    public static Categoria valueOf(Categoria categoria) {
        return categoria;
    }
}
