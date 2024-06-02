package com.tcc.entrepaginas.modules.books.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcc.entrepaginas.exceptions.CustomException;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.books.entities.ImagemLivro;
import com.tcc.entrepaginas.modules.books.entities.Livro;
import com.tcc.entrepaginas.modules.books.entities.enums.Categoria;
import com.tcc.entrepaginas.modules.books.entities.enums.Estado;
import com.tcc.entrepaginas.modules.books.entities.enums.EstadoBrasil;
import com.tcc.entrepaginas.modules.books.entities.enums.Tipo;
import com.tcc.entrepaginas.modules.books.repositories.ImagemLivroRepository;
import com.tcc.entrepaginas.modules.books.repositories.LivroRepository;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;

@Service
public class BookService {

    private final Path root = Paths.get("bookImage");

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ImagemLivroRepository imagemLivroRepository;

    public Livro buscarLivro(String id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.orElseThrow(() -> new ResourceNotFound(id));
    }

    public List<Livro> listarLivros(Sort sort) {
        return livroRepository.findAll(sort);
    }

    public void salvarLivro(Livro livro, String idUsuario, List<ImagemLivro> imagensLivro) {
        if (livro == null) {
            throw new IllegalArgumentException("Dados inválidos");
        }

        livro.setUsuario(usuarioService.pegarUsuario(idUsuario));

        livroRepository.save(livro);

        for (ImagemLivro imagemLivro : imagensLivro) {
            imagemLivroRepository.save(imagemLivro);
        }
    }

    public void atualizarLivro(Livro livro) {
        livroRepository.save(livro);
    }

    public void apagarLivroPorId(String id) {
        Livro livro = this.buscarLivro(id);
        livroRepository.delete(livro);
    }

    public List<String> listarTodosEstadoBrasil() {
        return Arrays.stream(EstadoBrasil.values())
                .map(EstadoBrasil::name)
                .collect(Collectors.toList());
    }

    public EstadoBrasil pegarEstadoBrasilPorNome(String nomeEstadoBrasil) {
        try {
            return EstadoBrasil.valueOf(nomeEstadoBrasil);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<String> listarTodasCategorias() {
        return Arrays.stream(Categoria.values())
                .map(Categoria::name)
                .collect(Collectors.toList());
    }

    public Categoria pegarCategoriaPorNome(String nomeCategoria) {
        try {
            return Categoria.valueOf(nomeCategoria);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<String> listarTodosEstados() {
        return Arrays.stream(Estado.values())
                .map(Estado::name)
                .collect(Collectors.toList());
    }

    public Estado pegarEstadoPorNome(String nomeEstado) {
        try {
            return Estado.valueOf(nomeEstado);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<String> listarTodosTipos() {
        return Arrays.stream(Tipo.values())
                .map(Tipo::name)
                .collect(Collectors.toList());
    }

    public Tipo pegarTipoPorNome(String nomeTipo) {
        try {
            return Tipo.valueOf(nomeTipo);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Livro getRandomLivro() {
        List<Livro> livros = livroRepository.findAll();
        int size = livros.size();

        if (size == 0) {
            throw new ResourceNotFound("Não há livros disponíveis.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(size);

        return livros.get(randomIndex);
    }

    public List<Livro> listarRandomLivros(int totalItems, Principal principal, String idTroca) {
        List<Livro> livros = this.listarLivros(Sort.by(Sort.Direction.ASC, "id"));

        if (principal != null) {
            String username = principal.getName();
            List<Livro> userCommunities = usuarioService.getUserLivros(username);
            livros.removeAll(userCommunities);
        }

        if (idTroca != null) {
            Livro livro = this.buscarLivro(idTroca);
            livros.remove(livro);
        }

        if (livros.size() > totalItems) {
            livros = this.getRandomLivros(livros, totalItems);
        }

        return livros;
    }

    public List<Livro> getRandomLivros(List<Livro> livro, int totalItems) {
        Collections.shuffle(livro);
        return livro.subList(0, totalItems);
    }

    public List<Livro> listarTrocasPorPessoas(String idUsuario) {
        Usuario usuario = usuarioService.pegarUsuario(idUsuario);
        return livroRepository.findByUsuario(usuario);
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
