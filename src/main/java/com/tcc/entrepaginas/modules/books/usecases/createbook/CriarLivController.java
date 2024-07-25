package com.tcc.entrepaginas.modules.books.usecases.createbook;

import com.tcc.entrepaginas.domain.entity.ImagemLivro;
import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.modules.books.service.BookServiceOld;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import com.tcc.entrepaginas.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/book")
public class CriarLivController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BookServiceOld bookServiceOld;

    private BookService bookService;
    @GetMapping("/create/{id}")
    public String livro(
            Model model, @PathVariable("id") String idUsuario, Authentication authentication ) {

        return bookService.beginBookCreation(model, idUsuario,authentication);
    }

    @PostMapping("/create/save/{id}")
    public String createLivro(
            @PathVariable("id") String idUsuario,
            @Valid Livro livro,
            @RequestParam("images") List<MultipartFile> imagens,
            HttpServletRequest request) {

        List<ImagemLivro> imagensLivro = new ArrayList<>();
        for (MultipartFile imagem : imagens) {
            String fileName = bookServiceOld.atualizarImagemLivro(imagem);
            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();
            String url = baseUrl + "/bookImage/" + fileName;

            ImagemLivro imagemLivro = new ImagemLivro(url, livro);
            imagensLivro.add(imagemLivro);
        }

        livro.setEstado(bookServiceOld.pegarEstadoPorNome(request.getParameter("estado")));
        livro.setTipo(bookServiceOld.pegarTipoPorNome(request.getParameter("tipo")));
        livro.setCategoria(bookServiceOld.pegarCategoriaPorNome(request.getParameter("categoria")));
        livro.setEstadoBrasil(bookServiceOld.pegarEstadoBrasilPorNome(request.getParameter("estadoBrasil")));

        bookServiceOld.salvarLivro(livro, idUsuario, imagensLivro);

        return "redirect:/book/create/" + idUsuario;
    }
}
