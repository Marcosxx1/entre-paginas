package com.tcc.entrepaginas.modules.users.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcc.entrepaginas.exceptions.CustomException;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.books.entities.Livro;
import com.tcc.entrepaginas.modules.community.entities.Community;
import com.tcc.entrepaginas.modules.community.entities.Membros;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final Path root = Paths.get("uploads");

    public Usuario pegarUsuario(String id) {
        Objects.requireNonNull(id, "ID must not be null");

        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ResourceNotFound(id));
    }

    public List<Usuario> listarUsuarios(Sort sort) {
        if (sort != null) {
            return usuarioRepository.findAll(sort);
        } else {
            return Collections.emptyList();
        }
    }

    public Usuario salvarUsuario(Usuario usuario) {
        Objects.requireNonNull(usuario, "Usuario must not be null");
        return usuarioRepository.save(usuario);
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

    public String atualizarImagemUsuario(MultipartFile image) {
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

    public void apagarUsuarioPorId(String id) {
        Usuario usuario = Objects.requireNonNull(this.pegarUsuario(id), "Usuario not found");
        usuarioRepository.delete(usuario);
    }

    public List<Usuario> buscarUsuarios(String query) {
        return usuarioRepository.findByNomeContainingIgnoreCase(query);
    }

    public List<Community> getUserCommunities(String username) {
        Usuario user = usuarioRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return user.getMembros().stream()
                .map(Membros::getCommunity)
                .collect(Collectors.toList());
    }

    public List<Livro> getUserLivros(String username) {
        Usuario user = usuarioRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return user.getLivros().stream()
                .collect(Collectors.toList());
    }

    public Usuario pegarUsuarioPorLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}