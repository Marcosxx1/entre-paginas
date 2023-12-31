package com.tcc.entrepaginas.modules.books.usecases.listbooks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.entrepaginas.modules.books.entities.Livro;
import com.tcc.entrepaginas.modules.books.service.BookService;

@RestController
@RequestMapping("/api/livros")
public class ListarLivController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String listarLivros(Model model) {
        List<Livro> livros = bookService.listarLivros(Sort.by(Sort.Direction.ASC, "id"));

        model.addAttribute("livros", livros);
        return "/Alguma Coisa";
    }
}
