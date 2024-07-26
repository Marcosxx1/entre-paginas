package com.tcc.entrepaginas.modules.books.usecases;

import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.modules.books.service.BookServiceOld;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookControllerOld {

    @Autowired
    public BookServiceOld bookServiceOld;

    @Autowired
    public UsuarioRepository usuarioRepository;

    @GetMapping("/book/exchanges/{id}")
    public String tradeBook(
            Model model, @PathVariable("id") String idUsuario, Authentication authentication, Principal principal) {

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario user = usuarioRepository.findByLogin(username);
            model.addAttribute("user", user);
        }

        model.addAttribute("livrosTrocar", bookServiceOld.listarTrocasPorPessoas(idUsuario));

        return "MinhasTrocas";
    }

    @GetMapping("/book/trade/{id}")
    public String book(
            Model model, @PathVariable("id") String idTroca, Authentication authentication, Principal principal) {

        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario user = usuarioRepository.findByLogin(username);
            model.addAttribute("user", user);
        }

        model.addAttribute("books", bookServiceOld.listarRandomLivros(10, principal, idTroca));
        model.addAttribute("troca", bookServiceOld.buscarLivro(idTroca));

        return "Book";
    }
}
