package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoLivroRequest;
import com.tcc.entrepaginas.domain.entity.ImagemLivro;
import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.domain.enums.Categoria;
import com.tcc.entrepaginas.domain.enums.Estado;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
import com.tcc.entrepaginas.domain.enums.Tipo;
import com.tcc.entrepaginas.exceptions.CustomException;
import com.tcc.entrepaginas.mapper.book.BookMapper;
import com.tcc.entrepaginas.repository.LivroRepository;
import com.tcc.entrepaginas.utils.GetUserIdFromContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final LivroRepository livroRepository;
    private final ImagemLivroService imagemLivroService;
    private final GetUserIdFromContext getUserIdFromContext;
    private final BookMapper bookMapper;

    private Path root = Paths.get("bookImage");

    @Override
    public String beginBookCreation(Model model, String idUsuario, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
             Usuario user = getUserIdFromContext.getUserById(idUsuario);
            model.addAttribute("user", user);
        }

        model.addAttribute("livro", new Livro());
        model.addAttribute("categorias", listarTodasCategorias());
        model.addAttribute("estados", listarTodosEstados());
        model.addAttribute("tipos", listarTodosTipos());
        model.addAttribute("estadosBrasil", listarTodosEstadoBrasil());
        model.addAttribute("idUsuario", idUsuario);

        return "TrocarLivro";
    }

    @Override
    public String saveBook(String idUsuario, NovoLivroRequest novoLivroRequest, List<MultipartFile> imagens, HttpServletRequest request) {

        List<ImagemLivro> imagensLivro = new ArrayList<>();
        Livro livroParaAssociar = bookMapper.toLivro(novoLivroRequest);

        for (MultipartFile imagem : imagens) {
            String fileName = atualizarImagemLivro(imagem);
            String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                    .replacePath(null)
                    .build()
                    .toUriString();
            String url = baseUrl + "/bookImage/" + fileName;


            ImagemLivro imagemLivro = new ImagemLivro(url, livroParaAssociar);
            imagensLivro.add(imagemLivro);
        }
        //livroParaAssociar.setImagens(imagensLivro);

        novoLivroRequest.setEstado(pegarEstadoPorNome(request.getParameter("estado")));
        novoLivroRequest.setTipo(pegarTipoPorNome(request.getParameter("tipo")));
        novoLivroRequest.setCategoria(pegarCategoriaPorNome(request.getParameter("categoria")));
        novoLivroRequest.setEstadoBrasil(pegarEstadoBrasilPorNome(request.getParameter("estadoBrasil")));

        salvarLivro(livroParaAssociar, idUsuario, imagensLivro);

        return "redirect:/book/create/" + idUsuario;
    }

    public void salvarLivro(Livro livro, String idUsuario, List<ImagemLivro> imagensLivro) {
        if (livro == null) {
            throw new IllegalArgumentException("Dados inválidos");
        }

        livro.setUsuario(getUserIdFromContext.getUserById(idUsuario));

        livroRepository.save(livro);

        for (ImagemLivro imagemLivro : imagensLivro) {
            imagemLivroService.saveImagemLivro(imagemLivro);
        }
    }

    public EstadoBrasil pegarEstadoBrasilPorNome(String nomeEstadoBrasil) {
        try {
            return EstadoBrasil.valueOf(nomeEstadoBrasil);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Categoria pegarCategoriaPorNome(String nomeCategoria) {
        try {
            return Categoria.valueOf(nomeCategoria);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Estado pegarEstadoPorNome(String nomeEstado) {
        try {
            return Estado.valueOf(nomeEstado);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Tipo pegarTipoPorNome(String nomeTipo) {
        try {
            return Tipo.valueOf(nomeTipo);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<String> listarTodasCategorias() {
        return Arrays.stream(Categoria.values()).map(Categoria::name).collect(Collectors.toList());
    }

    public List<String> listarTodosEstados() {
        return Arrays.stream(Estado.values()).map(Estado::name).collect(Collectors.toList());
    }

    public List<String> listarTodosTipos() {
        return Arrays.stream(Tipo.values()).map(Tipo::name).collect(Collectors.toList());
    }

    public List<String> listarTodosEstadoBrasil() {
        return Arrays.stream(EstadoBrasil.values()).map(EstadoBrasil::name).collect(Collectors.toList());
    }

    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new CustomException("Could not initialize folder for upload!");
        }
    }

    public String atualizarImagemLivro(MultipartFile image) {
        try {
            init();

            UUID uuid = UUID.randomUUID();

            String newFileName = uuid.toString();

            String extension = FilenameUtils.getExtension(image.getOriginalFilename());

            String fileName = newFileName + "." + extension;

            Files.copy(image.getInputStream(), this.root.resolve(fileName));

            return fileName;
        } catch (Exception e) {
            throw new CustomException("Could not store the file. Error: " + e.getMessage());
        }
    }
}
