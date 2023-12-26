package com.tcc.entrepaginas.modules.books.usecases.createbook;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.tcc.entrepaginas.modules.books.entities.ImagemLivro;
import com.tcc.entrepaginas.modules.books.entities.Livro;
import com.tcc.entrepaginas.modules.books.service.BookService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/book")
public class CriarLivController {

    @Autowired
    private BookService bookService;

    @GetMapping("/create/{id}")
    public String livro(Model model, @PathVariable("id") String idUsuario) {

        model.addAttribute("livro", new Livro());
        model.addAttribute("categorias", bookService.listarTodasCategorias());
        model.addAttribute("estados", bookService.listarTodosEstados());
        model.addAttribute("tipos", bookService.listarTodosTipos());
        model.addAttribute("idUsuario", idUsuario);

        return "TrocarLivro";
    }

    @PostMapping("/create/save/{id}")
    public String createLivro(@PathVariable("id") String idUsuario, @Valid Livro livro,
            @RequestParam("images") List<MultipartFile> imagens, BindingResult result,
            RedirectAttributes attributes, Model model, HttpServletRequest request) {

        List<ImagemLivro> imagensLivro = new ArrayList<>();
        for (MultipartFile imagem : imagens) {
            String fileName = bookService.atualizarImagemLivro(imagem);
            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build()
                    .toUriString();
            String url = baseUrl + "/bookImage/" + fileName;

            ImagemLivro imagemLivro = new ImagemLivro(url, livro);
            imagensLivro.add(imagemLivro);
        }

        livro.setEstado(bookService.pegarEstadoPorNome(request.getParameter("estado")));
        livro.setTipo(bookService.pegarTipoPorNome(request.getParameter("tipo")));
        livro.setCategoria(bookService.pegarCategoriaPorNome(request.getParameter("categoria")));

        bookService.salvarLivro(livro, idUsuario, imagensLivro);

        return "redirect:/book/create/" + idUsuario;
    }

}