package com.tcc.entrepaginas.service.book;

import com.tcc.entrepaginas.domain.dto.LivroParaEditarRequest;
import com.tcc.entrepaginas.domain.dto.NovoLivroRequest;
import com.tcc.entrepaginas.domain.entity.ImagemLivro;
import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.mapper.book.BookMapper;
import com.tcc.entrepaginas.repository.LivroRepository;
import com.tcc.entrepaginas.service.enums.EnumConverterService;
import com.tcc.entrepaginas.service.enums.EnumListingService;
import com.tcc.entrepaginas.service.imagemlivro.ImagemLivroService;
import com.tcc.entrepaginas.service.user.UserService;
import com.tcc.entrepaginas.utils.user.UserUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
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
    private final UserService userService;
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

        return "redirect:/book/exchanges/" + idUsuario;
    }

    @Override
    public String bookExchange(Model model, String idUsuario, Authentication authentication) {
        model = userUtils.setUserInAttributesIfAuthenticated(model, authentication, idUsuario);

        model.addAttribute("livrosTrocar", listarTrocasPorPessoas(idUsuario));
        return "MinhasTrocas";
    }

    // @Override
    // public String trocaDeLivroPorRegiao(Model model, Authentication
    // authentication) {

    // var usuario = userUtils.getUsuarioObjectFromAuthentication(authentication);

    // model = userUtils.setUserInAttributesIfAuthenticated(model, authentication,
    // usuario.getId());

    // List<Livro> livrosPorRegiao = listarLivrosPorRegiao(usuario.getCidade(),
    // usuario.getEstadoBrasil());

    // model.addAttribute("todosOsLivrosDoUsuario",
    // listarTrocasPorPessoas(usuario.getId()));
    // model.addAttribute("todosOsLivosDaRegiao", livrosPorRegiao);
    // return "MinhasTrocas";
    // }

    @Override
    public List<Livro> trocaDeLivroPorRegiao(Model model, Authentication authentication) {

        var usuario = userUtils.getUsuarioObjectFromAuthentication(authentication);

        model = userUtils.setUserInAttributesIfAuthenticated(model, authentication, usuario.getId());

        List<Livro> livrosPorRegiao = listarLivrosPorRegiao(usuario.getCidade(), usuario.getEstadoBrasil());

        return livrosPorRegiao;
    }

    private void salvarLivro(Livro livro, String idUsuario, List<ImagemLivro> imagensLivro) {

        livro.setUsuario(userUtils.getUserById(idUsuario));
        livroRepository.save(livro);

        for (ImagemLivro imagemLivro : imagensLivro) {
            imagemLivroService.saveImagemLivro(imagemLivro);
        }
    }

    public List<Livro> listarTrocasPorPessoas(String idUsuario) {
        Usuario usuario = userUtils.getUserById(idUsuario);
        return livroRepository.findByUsuario(usuario).orElseThrow(() -> new ResourceNotFound(idUsuario));
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
    public Livro getRandomLivro(Authentication authentication) {
        List<Livro> livros = livroRepository.findAll();

        Usuario user = userUtils.getUserById(userUtils.getIdUserFromUserDetail(authentication));

        if (user != null) {
            livros = livros.stream()
                    .filter(livro -> !livro.getUsuario().getNome().equals(user.getNome()))
                    .collect(Collectors.toList());
        }

        if (livros.isEmpty()) {
            return null;
        }

        return livros.get(new Random().nextInt(livros.size()));
    }

    @Override
    public String apagarLivroPorIdAdmin(String id) {
        Livro livro = buscarLivro(id);
        livroRepository.delete(livro);

        return "redirect:/admin";
    }
    // @Override
    // public Livro getRandomLivro(Authentication authentication) {
    // List<Livro> livros = livroRepository.findAll();

    // Usuario user =
    // userUtils.getUserById(userUtils.getIdUserFromUserDetail(authentication));

    // if (!(livros.isEmpty()) && user != null) {

    // livros = livros.stream().filter(livro ->
    // livro.getUsuario().getNome().equals(user.getNome()))
    // .collect(Collectors.toList());
    // } else {

    // new Livro();
    // }

    // return livros.get(new Random().nextInt(livros.size()));
    // }

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
            List<Livro> userLivros = userService.getUserLivros(username);
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

    public List<Livro> listAllBooksForUser(Usuario user) {
        log.error("listAllBooksForUser [{}]", user.getId());
        return livroRepository.findByUsuario(user).orElseThrow(() -> new ResourceNotFound(user.getId()));
    }

    public String prepareBookToEdit(Model model, String idLivro, Authentication authentication) {
        String idUsuario = userUtils.getIdUserFromUserDetail(authentication);
        model = userUtils.setUserInAttributesIfAuthenticated(model, authentication, idUsuario);

        Livro livro = buscarLivro(idLivro);
        model.addAttribute("livro", livro);
        model.addAttribute("categorias", enumListingService.listarTodasCategorias());
        model.addAttribute("estados", enumListingService.listarTodosEstados());
        model.addAttribute("tipos", enumListingService.listarTodosTipos());
        model.addAttribute("estadosBrasil", enumListingService.listarTodosEstadosBrasil());
        model.addAttribute("idUsuario", livro.getUsuario().getId());

        return "EditarLivro";
    }

    @Override
    public String saveEditedBook(
            String idLivro, LivroParaEditarRequest livroParaEditarRequest, HttpServletRequest request) {
        Livro livro = buscarLivro(idLivro);
        livro = bookMapper.toLivroFromLivroParaEditarRequest(livro, livroParaEditarRequest);
        livroRepository.save(livro);
        return "redirect:/book/exchanges/" + livro.getUsuario().getId();
    }

    @Override
    public String prepareTradeBookPage(
            Model model, String idTroca, Authentication authentication, Principal principal) {

        String idUsuario = userUtils.getIdUserFromUserDetail(authentication);
        model = userUtils.setUserInAttributesIfAuthenticated(model, authentication, idUsuario);

        model.addAttribute("books", listarRandomLivros(10, principal, idTroca));
        model.addAttribute("troca", buscarLivro(idTroca));

        return "Book";
    }

    @Override
    public List<Livro> listarLivrosPorRegiao(String userCidade, EstadoBrasil userEstado) {
        return livroRepository
                .findByCidadeAndEstadoBrasil(userCidade, userEstado)
                .orElseThrow(() -> new ResourceNotFound(userCidade));
    }

    @Override
    public List<Livro> listarTodasTrocas(Authentication authentication) {

        List<Livro> livros = listarLivros(Sort.by(Sort.Direction.ASC, "id"));

        Usuario user = userUtils.getUserById(userUtils.getIdUserFromUserDetail(authentication));

        if (user != null) {
            livros = livros.stream()
                    .filter(livro -> !livro.getUsuario().getNome().equals(user.getNome()))
                    .collect(Collectors.toList());
        }

        return livros;
    }
}
