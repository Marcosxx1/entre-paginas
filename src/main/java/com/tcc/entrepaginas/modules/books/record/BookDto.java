package com.tcc.entrepaginas.modules.books.record;

import com.tcc.entrepaginas.modules.books.entities.Livro;
import com.tcc.entrepaginas.modules.books.entities.enums.Categoria;
import com.tcc.entrepaginas.modules.books.entities.enums.Estado;
import com.tcc.entrepaginas.modules.books.entities.enums.Tipo;

public record BookDto(String id, String nome, String descricao, String editora, Estado estado,
                Tipo tipo, Categoria categoria, Double preco) {

        public static BookDto fromBookDto(Livro livro) {
                return new BookDto(
                                livro.getId(),
                                livro.getNome(),
                                livro.getDescricao(),
                                livro.getEditora(),
                                livro.getEstado(),
                                livro.getTipo(),
                                livro.getCategoria(),
                                livro.getPreco());
        }

}