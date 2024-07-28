package com.tcc.entrepaginas.modules.books.record;

import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.enums.Categoria;
import com.tcc.entrepaginas.domain.enums.Estado;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
import com.tcc.entrepaginas.domain.enums.Tipo;

public record BookDto(
        String id,
        String nome,
        String descricao,
        Estado estado,
        Tipo tipo,
        Categoria categoria,
        EstadoBrasil estadoBrasil,
        String cidade) {

    public static BookDto fromBookDto(Livro livro) {
        return new BookDto(
                livro.getId(),
                livro.getNome(),
                livro.getDescricao(),
                livro.getEstado(),
                livro.getTipo(),
                livro.getCategoria(),
                livro.getEstadoBrasil(),
                livro.getCidade());
    }
}
