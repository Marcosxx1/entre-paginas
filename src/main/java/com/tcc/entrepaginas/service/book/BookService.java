package com.tcc.entrepaginas.service.book;

import com.tcc.entrepaginas.domain.dto.LivroParaEditarRequest;
import com.tcc.entrepaginas.domain.dto.NovoLivroRequest;
import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
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

    List<Livro> trocaDeLivroPorRegiao(Model model, Authentication authentication);

    Livro getRandomLivro(Authentication authentication);

    List<Livro> listAllBooksForUser(Usuario user);

    String prepareBookToEdit(Model model, String idLivro, Authentication authentication);

    String saveEditedBook(String idLivro, LivroParaEditarRequest livroParaEditarRequest, HttpServletRequest request);

    String prepareTradeBookPage(Model model, String idTroca, Authentication authentication, Principal principal);

    List<Livro> listarLivrosPorRegiao(String userCidade, EstadoBrasil userEstado);

    List<Livro> listarTodasTrocas(Authentication authentication);

    String apagarLivroPorIdAdmin(String id);
}
