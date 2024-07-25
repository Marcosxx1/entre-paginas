package com.tcc.entrepaginas.modules.books.usecases.deletebook;

import com.tcc.entrepaginas.modules.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/book")
public class DeletarLivroController {

    @Autowired
    private BookService bookService;

    @GetMapping("/delete/{id}")
    public String deletarLivro(@PathVariable String id, RedirectAttributes attributes, Model model) {

        bookService.apagarLivroPorId(id);

        return "";
    }
}
