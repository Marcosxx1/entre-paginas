package com.tcc.entrepaginas.modules.books.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcc.entrepaginas.modules.books.service.BookService;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;

@Controller
public class BookController {

    @Autowired
    public BookService bookService;

    @Autowired
    public UsuarioRepository usuarioRepository;

    @GetMapping("/book/exchanges/{id}")
    public String tradeBook(Model model, @PathVariable("id") String idUsuario) {

        model.addAttribute("livrosTrocar", bookService.listarTrocasPorPessoas(idUsuario));

        return "MinhasTrocas";
    }

    @GetMapping("/book/trade/{id}")
    public String book(Model model, @PathVariable("id") String idTroca, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario user = usuarioRepository.findByLogin(username);
            model.addAttribute("user", user);
        }

        model.addAttribute("troca", bookService.buscarLivro(idTroca));

        return "Book";
    }

}
