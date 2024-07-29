package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.LivroParaEditarRequest;
import com.tcc.entrepaginas.domain.dto.NovoLivroRequest;
import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

    String beginBookCreation(Model model, String idUsuario, Authentication authentication);

    String saveBook(
            String idUsuario,
            NovoLivroRequest novoLivroRequest,
            List<MultipartFile> imagens,
            HttpServletRequest request);

    String bookExchange(Model model, String idUsuario, Authentication authentication);

    List<Livro> listarRandomLivros(int totalItems, Principal principal, String idTroca);

    List<Livro> listarLivros(Sort sort);

    Livro buscarLivro(String id);

    void apagarLivroPorId(String id);

    Livro getRandomLivro();

    List<Livro> listAllBooksForUser(Usuario user);
    /*    @GetMapping("/book/prepare-edit/")
    public String beginBookEdit(Model model, @PathVariable("id") String idLivro, Authentication authentication) {

        log.info("BookController - GET on /book/edit/{id};  /book/edit/{}, called by user: {}", idLivro, authentication != null ? authentication.getName() : "Anonymous");

        return bookService.prepareBookToEdit(model, idLivro, authentication);
    }*/
    String prepareBookToEdit(Model model, String idLivro, Authentication authentication);

    String saveEditedBook(String idLivro, LivroParaEditarRequest livroParaEditarRequest, HttpServletRequest request);
}
