package com.tcc.entrepaginas.service.index;

import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.mapper.user.UserMapper;
import com.tcc.entrepaginas.service.book.BookService;
import com.tcc.entrepaginas.service.comments.CommentsServiceNew;
import com.tcc.entrepaginas.service.community.CommunityServiceNew;
import com.tcc.entrepaginas.service.post.PostServiceNew;
import com.tcc.entrepaginas.service.reaction.ReactionServiceNew;
import com.tcc.entrepaginas.service.user.UserService;
import com.tcc.entrepaginas.utils.user.UserUtils;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final UserUtils userUtils;
    private final UserMapper userMapper;
    private final BookService bookService;
    private final UserService userService;
    private final PostServiceNew postServiceNew;
    private final CommentsServiceNew commentsServiceNew;
    private final ReactionServiceNew reactionServiceNew;
    private final CommunityServiceNew communityServiceNew;

    @Override
    public String redirecctToIndexOrLoginBasedOnAuth(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";
        }
        return "/Login";
    }

    @Override
    public String populateIndexModel(Model model, Principal principal, Authentication authentication) {
        model = userUtils.setModelIfAuthenticationExists(authentication, model);

        model.addAttribute("books", bookService.listarRandomLivros(10, principal, null));
        model.addAttribute("listPost", postServiceNew.listarPost(Sort.by(Sort.Direction.DESC, "date")));
        model.addAttribute("comunidades", communityServiceNew.listarRandomCommunities(4, principal));
        model.addAttribute("comments", commentsServiceNew.listarComments(Sort.by(Sort.Direction.ASC, "id")));
        model.addAttribute("qtdReaction", reactionServiceNew.countReaction());
        model.addAttribute("book", bookService.getRandomLivro(authentication));

        return "/Index";
    }

    @Override
    public String populateModelForProfileView(Model model, Authentication authentication, Principal principal) {
        if (authentication != null && authentication.isAuthenticated()) { // Usar do UserUtils
            String username = authentication.getName();

            model = userUtils.setModelIfAuthenticationExists(authentication, model);
            model.addAttribute("listPost", postServiceNew.listAllPostsInACommunity(username));
            model.addAttribute("books", bookService.listarRandomLivros(10, principal, null));
            model.addAttribute("comments", commentsServiceNew.listarComments(Sort.by(Sort.Direction.ASC, "id")));
            model.addAttribute("qtdReaction", reactionServiceNew.countReaction());
        }

        return "/Perfil";
    }

    @Override
    public String prepareUser(Model model, Authentication authentication) {

        var updateUserNameLoginAndEmailRequest = userMapper.toUpdateUserNameLoginAndEmailRequest(authentication);

        model = userUtils.setModelIfAuthenticationExists(authentication, model);
        model.addAttribute("updateUserNameLoginAndEmailRequest", updateUserNameLoginAndEmailRequest);
        return "InformacoesUsuario";
    }

    @Override
    public String visitOtherUsersFromIndex(Model model, String userName, Authentication authentication) {
        Usuario user = userUtils.getUserByLogin(userName);

        if (user == null) {
            return "redirect:/error/404";
        }

        model = userUtils.setModelIfAuthenticationExists(authentication, model);
        model.addAttribute("perfilVisitante", user);
        model.addAttribute("books", bookService.listAllBooksForUser(user));
        model.addAttribute("communities", communityServiceNew.listCommunitiesForUser(user));

        return "/Perfil";
    }

    @Override
    public String suporte(Model model, Principal principal, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) { // Usar do UserUtils
            String username = authentication.getName();

            model = userUtils.setModelIfAuthenticationExists(authentication, model);
            model.addAttribute("listPost", postServiceNew.listAllPostsInACommunity(username));
            model.addAttribute("books", bookService.listarRandomLivros(10, principal, null));
            model.addAttribute("comments", commentsServiceNew.listarComments(Sort.by(Sort.Direction.ASC, "id")));
            model.addAttribute("qtdReaction", reactionServiceNew.countReaction());
        }

        return "Suporte";
    }

    @Override
    public String telaAdmin(Model model, Principal principal, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {
            Usuario user = userUtils.getUserByLogin(authentication.getName());

            // if (!user.getPapel().equals("ADMIN")) {
            // return "redirect:/index";
            // }

            model = userUtils.setModelIfAuthenticationExists(authentication, model);
            model.addAttribute("users", userService.listUser(Sort.by(Sort.Direction.ASC, "id")));
            model.addAttribute("books", bookService.listarLivros(Sort.by(Sort.Direction.ASC, "id")));
            model.addAttribute("listPost", postServiceNew.listarPost(Sort.by(Sort.Direction.ASC, "id")));
        }

        return "TelaAdmin";
    }
}
