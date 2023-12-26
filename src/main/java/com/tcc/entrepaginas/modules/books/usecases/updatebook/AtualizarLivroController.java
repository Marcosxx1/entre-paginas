// package com.tcc.entrepaginas.modules.books.usecases.updatebook;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PatchMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.tcc.entrepaginas.modules.books.record.BookDto;
// import com.tcc.entrepaginas.modules.books.service.BookService;

// @RestController
// @RequestMapping("/api/livros")
// public class AtualizarLivroController {

//     @Autowired
//     private BookService bookSerice;

//     @PatchMapping("/{id}")
//     public String atualizarLivro(@PathVariable String id,
//             @RequestBody BookDto livroDTO) {

//         bookSerice.atualizarLivro(id, livroDTO);
//     }
// }
