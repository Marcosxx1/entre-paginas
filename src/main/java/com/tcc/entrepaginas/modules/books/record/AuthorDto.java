// package com.tcc.entre_paginasmarcketplace_de_livrosbackend.modules.books.record;

// import java.util.ArrayList;
// import java.util.List;

// import com.tcc.entre_paginasmarcketplace_de_livrosbackend.modules.books.entities.Autor;

// public record AuthorDto(String id, String name) {

//     public static AuthorDto fromAuthor(Autor author) {
//         return new AuthorDto(
//                 author.getId(),
//                 author.getNome());
//     }

//     public static List<AuthorDto> transformeAutorEmDto(List<Autor> autores) {
//         List<AuthorDto> authorDtos = new ArrayList<>();

//         for (Autor autor : autores) {
//             AuthorDto authorDto = AuthorDto.fromAuthor(autor);
//             authorDtos.add(authorDto);
//         }

//         return authorDtos;
//     }
// }
