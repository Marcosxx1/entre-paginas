package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoLivroRequest;
import com.tcc.entrepaginas.domain.entity.ImagemLivro;
import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.mapper.book.BookMapper;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import com.tcc.entrepaginas.repository.LivroRepository;
import com.tcc.entrepaginas.utils.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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
    private final UsuarioService usuarioService;
    private final UserUtils userUtils;
    private final EnumConverterService enumConverterService;
    private final EnumListingService enumListingService;

    private final BookMapper bookMapper;

    @Override
    public String beginBookCreation(Model model, String idUsuario, Authentication authentication) {
        model = userUtils.setUserInAttributesIfAuthenticated(model, authentication, idUsuario);

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

    @Override
    public String bookExchange(Model model, String idUsuario, Authentication authentication) {
        model = userUtils.setUserInAttributesIfAuthenticated(model, authentication, idUsuario);

        model.addAttribute("livrosTrocar", listarTrocasPorPessoas(idUsuario));
        return "MinhasTrocas";
    }

    private void salvarLivro(Livro livro, String idUsuario, List<ImagemLivro> imagensLivro) {
        if (livro == null) {
            throw new IllegalArgumentException("Dados inválidos"); // TODO - EXCEPTION MELHOR
        }

        livro.setUsuario(userUtils.getUserById(idUsuario));
        livroRepository.save(livro);

        for (ImagemLivro imagemLivro : imagensLivro) {
            imagemLivroService.saveImagemLivro(imagemLivro);
        }
    }

    public List<Livro> listarTrocasPorPessoas(String idUsuario) {
        Usuario usuario = userUtils.getUserById(idUsuario);
        return livroRepository.findByUsuario(usuario);
    }

    @Override
    public Livro buscarLivro(String id) {
        return livroRepository.findById(id).orElseThrow(() -> new ResourceNotFound(id));
    }

    @Override
    public List<Livro> listarLivros(Sort sort) {
        return livroRepository.findAll(sort);
    }

    @Override
    public void apagarLivroPorId(String id) {
        Livro livro = buscarLivro(id);
        livroRepository.delete(livro);
    }

    @Override
    public Livro getRandomLivro() {
        List<Livro> livros = livroRepository.findAll();
        return livros.isEmpty() ? new Livro() : livros.get(new Random().nextInt(livros.size()));
    }

    @Override
    public List<Livro> listarRandomLivros(int totalItems, Principal principal, String idTroca) {
        List<Livro> livros = livroRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        filterUserLivros(livros, principal);
        filterByIdTroca(livros, idTroca);

        return livros.size() > totalItems ? getRandomLivros(livros, totalItems) : livros;
    }

    private void filterUserLivros(List<Livro> livros, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            List<Livro> userLivros = usuarioService.getUserLivros(username);
            livros.removeAll(userLivros);
        }
    }

    private void filterByIdTroca(List<Livro> livros, String idTroca) {
        if (idTroca != null) {
            Livro livro = buscarLivro(idTroca);
            livros.remove(livro);
        }
    }

    private List<Livro> getRandomLivros(List<Livro> livros, int totalItems) {
        Collections.shuffle(livros);
        return livros.subList(0, totalItems);
    }
}