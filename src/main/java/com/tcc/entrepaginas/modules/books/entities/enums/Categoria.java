package com.tcc.entrepaginas.modules.books.entities.enums;

public enum Categoria {
    Fantasia(1),
    Ação(2),
    Ficção(3),
    Horror(4),
    Thriller(5),
    Romance(6),
    Novela(7),
    Realismo(8),
    Graphic_Novel(9),
    Conto(10),
    Infantil(11),
    Biografia(12),
    Gastronomia(13),
    Arte(14),
    Autoajuda(15),
    História(16),
    Viagem(17),
    Crimes(18),
    Humor(19),
    Ensaios(20),
    Guias(21),
    Religião(22),
    Ciências_Sociais(23),
    Família(24),
    Tecnologia(25);

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
