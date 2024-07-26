package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.NovoLivroRequest;
import com.tcc.entrepaginas.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/create/{id}")
    public String livro(Model model, @PathVariable("id") String idUsuario, Authentication authentication) {

        return bookService.beginBookCreation(model, idUsuario, authentication);
    }

    @PostMapping("/create/save/{id}")
    public String createLivro(
            @PathVariable("id") String idUsuario,
            @Valid NovoLivroRequest novoLivroRequest,
            @RequestParam("images") List<MultipartFile> imagens,
            HttpServletRequest request) {
        return bookService.saveBook(idUsuario, novoLivroRequest, imagens, request);
    }
}
