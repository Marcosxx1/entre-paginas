package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoLivroRequest;
import com.tcc.entrepaginas.domain.entity.ImagemLivro;
import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.mapper.book.BookMapper;
import com.tcc.entrepaginas.repository.LivroRepository;
import com.tcc.entrepaginas.utils.GetUserIdFromContext;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final LivroRepository livroRepository;
    private final ImagemLivroService imagemLivroService;
    private final GetUserIdFromContext getUserIdFromContext;
    private final EnumConverterService enumConverterService;
    private final EnumListingService enumListingService;

    private final BookMapper bookMapper;

    @Override
    public String beginBookCreation(Model model, String idUsuario, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            Usuario user = getUserIdFromContext.getUserById(idUsuario);
            model.addAttribute("user", user);
        }

        model.addAttribute("livro", new Livro());
        model.addAttribute("categorias", enumListingService.listarTodasCategorias());
        model.addAttribute("estados", enumListingService.listarTodosEstados());
        model.addAttribute("tipos", enumListingService.listarTodosTipos());
        model.addAttribute("estadosBrasil", enumListingService.listarTodosEstadosBrasil());
        model.addAttribute("idUsuario", idUsuario);

        return "TrocarLivro";
    }

    @Override
    public String saveBook(
            String idUsuario,
            NovoLivroRequest novoLivroRequest,
            List<MultipartFile> imagens,
            HttpServletRequest request) {

        Livro livroParaAssociar = bookMapper.toLivro(novoLivroRequest);
        List<ImagemLivro> imagensLivro = imagemLivroService.processImagens(imagens, livroParaAssociar, request);

        novoLivroRequest.setEstado(enumConverterService.convertEstado(request.getParameter("estado")));
        novoLivroRequest.setTipo(enumConverterService.convertTipo(request.getParameter("tipo")));
        novoLivroRequest.setCategoria(enumConverterService.convertCategoria(request.getParameter("categoria")));
        novoLivroRequest.setEstadoBrasil(
                enumConverterService.convertEstadoBrasil(request.getParameter("estadoBrasil")));

        salvarLivro(livroParaAssociar, idUsuario, imagensLivro);

        return "redirect:/book/create/" + idUsuario;
    }

    private void salvarLivro(Livro livro, String idUsuario, List<ImagemLivro> imagensLivro) {
        if (livro == null) {
            throw new IllegalArgumentException("Dados inválidos"); // TODO - EXCEPTION MELHOR
        }

        livro.setUsuario(getUserIdFromContext.getUserById(idUsuario));
        livroRepository.save(livro);

        for (ImagemLivro imagemLivro : imagensLivro) {
            imagemLivroService.saveImagemLivro(imagemLivro);
        }
    }
}
