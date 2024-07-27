package com.tcc.entrepaginas.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import com.tcc.entrepaginas.service.IndexService;
import com.tcc.entrepaginas.service.PostServiceNew;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;

    @Autowired
    private UsuarioRepository usuarioRepository; // Isso vai sair

    @Autowired
    private UsuarioService usuarioService; // Isso vai sair

    @Autowired
    private PostServiceNew postService; // também

    @GetMapping("/login")
    public String login(Authentication authentication) {
        return indexService.redirecctToIndexOrLoginBasedOnAuth(authentication);
    }

    @GetMapping("/index")
    public String index(Model model, Principal principal, Authentication authentication) {
        return indexService.populateIndexModel(model, principal, authentication);
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication, Principal principal) {
        return indexService.populateModelForProfileView(model, authentication, principal);
    }

    @GetMapping("/infos")
    public String informacoesDoUsuario(Model model, Authentication authentication) {
        return indexService.prepareUser(model, authentication);
    }

    // @GetMapping("/perfilVisitante/{idUsuario}")
    // public String perfilVisitante(Model model, Principal principal, @PathVariable
    // String idUsuario) {

    // Usuario user = usuarioService.pegarUsuario(idUsuario);
    // model.addAttribute("user", user);
    // model.addAttribute("listPost",
    // postService.listAllPostsInACommunity(user.getId()));
    // model.addAttribute("books", bookService.listarRandomLivros(10, principal));
    // model.addAttribute("comments",
    // commentsService.listarComments(Sort.by(Sort.Direction.ASC, "id")));
    // model.addAttribute("qtdReaction", reactionService.countReaction());

    // return "/Perfil";
    // }

    @GetMapping("/perfilVisitante/{idUsuario}")
    public String perfilVisitante(Model model, @PathVariable String idUsuario) {
        Usuario user = usuarioRepository.findById(idUsuario).orElse(null);

        if (user == null) {
            // Se o usuário não for encontrado, redirecione ou apresente uma mensagem de
            // erro
            // Aqui estou redirecionando para uma página de erro 404
            return "redirect:/error/404";
        }

        model.addAttribute("user", user);
        model.addAttribute("listPost", postService.listAllPostsInACommunity(idUsuario));
        // model.addAttribute("books", bookService.listarRandomLivros(10, null, null));
        // model.addAttribute("comments",
        // commentsService.listarComments(Sort.by(Sort.Direction.ASC, "id")));
        // model.addAttribute("qtdReaction", reactionService.countReaction());

        return "/Perfil";
    }
}