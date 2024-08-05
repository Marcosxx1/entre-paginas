package com.tcc.entrepaginas.mapper.book;

import com.tcc.entrepaginas.domain.dto.LivroParaEditarRequest;
import com.tcc.entrepaginas.domain.dto.NovoLivroRequest;
import com.tcc.entrepaginas.domain.entity.Livro;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Livro toLivro(NovoLivroRequest novoLivroRequest) {
        return Livro.builder()
                .nome(novoLivroRequest.getNome())
                .descricao(novoLivroRequest.getDescricao())
                .cidade(novoLivroRequest.getCidade())
                .estadoBrasil(novoLivroRequest.getEstadoBrasil())
                .estado(novoLivroRequest.getEstado())
                .tipo(novoLivroRequest.getTipo())
                .categoria(novoLivroRequest.getCategoria())
                .build();
    }

    public Livro toLivroFromLivroParaEditarRequest(Livro livro, LivroParaEditarRequest livroParaEditarRequest) {
        livro.setNome(livroParaEditarRequest.getNome());
        livro.setDescricao(livroParaEditarRequest.getDescricao());
        livro.setCidade(livroParaEditarRequest.getCidade());
        livro.setEstadoBrasil(livroParaEditarRequest.getEstadoBrasil());
        livro.setEstado(livroParaEditarRequest.getEstado());
        livro.setTipo(livroParaEditarRequest.getTipo());
        livro.setCategoria(livroParaEditarRequest.getCategoria());
        return livro;
    }
}
