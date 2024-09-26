package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.LivroParaEditarRequest;
import com.tcc.entrepaginas.domain.dto.NovoLivroRequest;
import com.tcc.entrepaginas.service.book.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @GetMapping("/create/{id}")
    public String livro(Model model, @PathVariable("id") String idUsuario, Authentication authentication) {

        log.info(
                "BookController - GET on /create/{id};  /create/{}, called by user: {}",
                idUsuario,
                authentication != null ? authentication.getName() : "Anonymous");

        return bookService.beginBookCreation(model, idUsuario, authentication);
    }

    @PostMapping("/create/save/{id}")
    public String createLivro(
            @PathVariable("id") String idUsuario,
            @Valid NovoLivroRequest novoLivroRequest,
            @RequestParam("images") List<MultipartFile> imagens,
            HttpServletRequest request) {

        log.info(
                "BookController - POST on /create/save/{id};  /create/save/{}, called with NovoLivroRequest: {}, number of images: {}",
                idUsuario,
                novoLivroRequest,
                imagens != null ? imagens.size() : 0);

        return bookService.saveBook(idUsuario, novoLivroRequest, imagens, request);
    }

    @GetMapping("/exchanges/{id}")
    public String tradeBook(Model model, @PathVariable("id") String idUsuario, Authentication authentication) {

        log.info(
                "BookController - GET on /exchanges/{id};  /exchanges/{}, called by user: {}",
                idUsuario,
                authentication != null ? authentication.getName() : "Anonymous");

        return bookService.bookExchange(model, idUsuario, authentication);
    }

    // @GetMapping("/exchanges-all-regions")
    // public String tradeBookAllRegions(Model model, Authentication authentication)
    // {
    // return bookService.trocaDeLivroPorRegiao(model, authentication);
    // }

    @GetMapping("/prepare-edit/{id}")
    public String editBook(Model model, @PathVariable("id") String idLivro, Authentication authentication) {

        log.info(
                "BookController - GET on /book/edit/{id};  /book/edit/{}, called by user: {}",
                idLivro,
                authentication != null ? authentication.getName() : "Anonymous");

        return bookService.prepareBookToEdit(model, idLivro, authentication);
    }

    @PostMapping("/edit/save/{id}")
    public String createLivro(
            @PathVariable("id") String idLivro,
            @Valid LivroParaEditarRequest livroParaEditarRequest,
            HttpServletRequest request) {

        log.info(
                "BookController - POST on /edit/save/{id}; /edit/save/{}, called with LivroParaEditarRequest: {}",
                idLivro,
                livroParaEditarRequest);

        return bookService.saveEditedBook(idLivro, livroParaEditarRequest, request);
    }

    @GetMapping("/trade/{id}")
    public String book(
            Model model, @PathVariable("id") String idTroca, Authentication authentication, Principal principal) {

        log.info(
                "BookController - GET on /book/trade/{id};  /book/trade/{}, called by user: {}",
                idTroca,
                authentication != null ? authentication.getName() : "Anonymous");
        return bookService.prepareTradeBookPage(model, idTroca, authentication, principal);
    }

    // @GetMapping("/all-exchanges")
    // public String viewAllExchanges(Model model, Authentication authentication) {
    // log.info(
    // "BookController - GET on /all-exchanges; called by user: {}",
    // authentication != null ? authentication.getName() : "Anonymous");

    // return bookService.listarTodasTrocas(model);
    // }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id) {
        log.info("BookController - GET on /delete/{}; called to delete user with id: ", id);
        return bookService.apagarLivroPorIdAdmin(id);
    }
}
