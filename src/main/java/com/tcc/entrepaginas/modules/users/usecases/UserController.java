package com.tcc.entrepaginas.modules.users.usecases;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.entrepaginas.modules.books.service.BookService;
import com.tcc.entrepaginas.modules.community.service.CommentsService;
import com.tcc.entrepaginas.modules.community.service.CommunityService;
import com.tcc.entrepaginas.modules.community.service.PostService;
import com.tcc.entrepaginas.modules.community.service.ReactionService;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;

@Controller
public class UserController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private PostService postService;

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private ReactionService reactionService;

    @GetMapping("/login")
    public String login(Authentication authentication, Model model,
            RedirectAttributes redirectAttributes) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";
        }
        return "/Login";
    }

    @GetMapping("/index")
    public String index(Model model, Authentication authentication, Principal principal) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario user = usuarioRepository.findByLogin(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("books", bookService.listarRandomLivros(10, principal, null));
        model.addAttribute("listPost", postService.listarPost(Sort.by(Sort.Direction.DESC, "date")));
        model.addAttribute("comunidades", communityService.listarRandomCommunities(10, principal));
        model.addAttribute("comments", commentsService.listarComments(Sort.by(Sort.Direction.ASC, "id")));
        model.addAttribute("qtdReaction", reactionService.countReaction());

        return "/Index";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication, Principal principal) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();

            Usuario user = usuarioRepository.findByLogin(username);
            model.addAttribute("user", user);
            model.addAttribute("listPost", postService.listAllPostsInACommunity(username));
            model.addAttribute("books", bookService.listarRandomLivros(10, principal, null));
            model.addAttribute("comments", commentsService.listarComments(Sort.by(Sort.Direction.ASC, "id")));
            model.addAttribute("qtdReaction", reactionService.countReaction());
        }

        return "/Perfil";
    }

    // @GetMapping("/infos/{id}")
    // public String infos(Model model, @PathVariable String id) {

    // Usuario user = usuarioService.pegarUsuario(id);

    // model.addAttribute("user", user);
    // return "/InformacoesUsuario";
    // }

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

}
