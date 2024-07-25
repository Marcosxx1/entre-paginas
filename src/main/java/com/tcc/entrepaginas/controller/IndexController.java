package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.UpdateUserNameLoginAndEmailRequest;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.modules.books.service.BookServiceOld;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import com.tcc.entrepaginas.repository.CommentsService;
import com.tcc.entrepaginas.repository.CommunityService;
import com.tcc.entrepaginas.repository.PostService;
import com.tcc.entrepaginas.repository.ReactionService;
import com.tcc.entrepaginas.repository.UsuarioRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private PostService postService;

    @Autowired
    private BookServiceOld bookServiceOld;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private ReactionService reactionService;

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";
        }
        return "/Login";
    }

    @GetMapping("/index")
    public String index(Model model, Authentication authentication, Principal principal) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = principal.getName();
            Usuario user = usuarioRepository.findByLogin(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("books", bookServiceOld.listarRandomLivros(10, principal, null));
        model.addAttribute("listPost", postService.listarPost(Sort.by(Sort.Direction.DESC, "date")));
        model.addAttribute("comunidades", communityService.listarRandomCommunities(4, principal));
        model.addAttribute("comments", commentsService.listarComments(Sort.by(Sort.Direction.ASC, "id")));
        model.addAttribute("qtdReaction", reactionService.countReaction());
        model.addAttribute("book", bookServiceOld.getRandomLivro());

        return "/Index";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication, Principal principal) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario user = usuarioRepository.findByLogin(username);
            model.addAttribute("user", user);
            model.addAttribute("listPost", postService.listAllPostsInACommunity(username));
            model.addAttribute("books", bookServiceOld.listarRandomLivros(10, principal, null));
            model.addAttribute("comments", commentsService.listarComments(Sort.by(Sort.Direction.ASC, "id")));
            model.addAttribute("qtdReaction", reactionService.countReaction());
        }

        return "/Perfil";
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
