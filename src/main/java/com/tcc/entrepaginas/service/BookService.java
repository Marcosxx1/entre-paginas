package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoLivroRequest;
import com.tcc.entrepaginas.domain.entity.Livro;
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
}
