package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Livro;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.domain.enums.Categoria;
import com.tcc.entrepaginas.domain.enums.Estado;
import com.tcc.entrepaginas.domain.enums.EstadoBrasil;
import com.tcc.entrepaginas.domain.enums.Tipo;
import com.tcc.entrepaginas.repository.LivroRepository;
import com.tcc.entrepaginas.utils.GetUserIdFromContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final LivroRepository livroRepository;
    private final GetUserIdFromContext getUserIdFromContext;

    @Override
    public String beginBookCreation(Model model, String idUsuario, Authentication authentication) {


        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

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
}
