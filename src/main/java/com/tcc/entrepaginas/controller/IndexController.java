package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.UpdateUserNameLoginAndEmailRequest;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import com.tcc.entrepaginas.repository.CommunityService;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import com.tcc.entrepaginas.service.IndexService;
import com.tcc.entrepaginas.service.PostServiceNew;
import com.tcc.entrepaginas.utils.UserUtils;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;
    private final UserUtils userUtils; // Isso vai sair

    @Autowired
    private UsuarioRepository usuarioRepository; // Isso vai sair

    @Autowired
    private UsuarioService usuarioService; // Isso vai sair

    @Autowired
    private CommunityService communityService; // também

    @Autowired
    private PostServiceNew postService; // também

    @GetMapping("/login")
    public String login(Authentication authentication) {
        return indexService.redirecctToIndexOrLoginBasedOnAuth(authentication);
    }

    @GetMapping("/index")
    public String index(Model model, Principal principal) {
        return indexService.populateIndexModel(model, principal);
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication, Principal principal) {
        return indexService.populateModelForProfileView(model, authentication, principal);
    }

    @GetMapping("/infos/{id}")
    public String infos(Model model, @PathVariable String id) {
        Usuario user = usuarioService.pegarUsuario(id);

        UpdateUserNameLoginAndEmailRequest updateUserNameLoginAndEmailRequest =
                new UpdateUserNameLoginAndEmailRequest();
        updateUserNameLoginAndEmailRequest.setId(id);
        updateUserNameLoginAndEmailRequest.setNome(user.getNome());
        updateUserNameLoginAndEmailRequest.setEmail(user.getEmail());
        updateUserNameLoginAndEmailRequest.setLogin(user.getLogin());

        model.addAttribute("user", user);
        model.addAttribute("updateUserNameLoginAndEmailRequest", updateUserNameLoginAndEmailRequest);
        return "InformacoesUsuario";
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
