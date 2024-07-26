package com.tcc.entrepaginas.modules.books.service;

import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.exceptions.CustomException;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import com.tcc.entrepaginas.repository.ImagemLivroRepository;
import com.tcc.entrepaginas.repository.LivroRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BookServiceOld {

    private final Path root = Paths.get("bookImage");

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ImagemLivroRepository imagemLivroRepository;

    public Livro buscarLivro(String id) {
        return livroRepository.findById(id).orElseThrow(() -> new ResourceNotFound(id));
    }

    public List<Livro> listarLivros(Sort sort) {
        return livroRepository.findAll(sort);
    }

    public void apagarLivroPorId(String id) {
        Livro livro = this.buscarLivro(id);
        livroRepository.delete(livro);
    }

    public Livro getRandomLivro() {
        List<Livro> livros = livroRepository.findAll();
        int size = livros.size();

        if (size == 0) {
            return new Livro();
        }

        Random random = new Random();
        int randomIndex = random.nextInt(size);

        return livros.get(randomIndex);
    }

    public List<Livro> listarRandomLivros(int totalItems, Principal principal, String idTroca) {
        List<Livro> livros = livroRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

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

    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new CustomException("Could not initialize folder for upload!");
        }
    }
}
