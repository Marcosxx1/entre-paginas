package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.utils.UserUtils;
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
    private final BookService bookService;
    private final PostServiceNew postServiceNew;
    private final CommunityServiceNew CommunityServiceNew;
    private final CommentsServiceNew commentsServiceNew;
    private final ReactionServiceNew reactionServiceNew;

    @Override
    public String redirecctToIndexOrLoginBasedOnAuth(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";
        }
        return "/Login";
    }

    @Override
    public String populateIndexModel(Model model, Principal principal, Authentication authentication) {
        model = userUtils.setModelIfAuthenticationExists(principal, authentication, model);

        model.addAttribute("books", bookService.listarRandomLivros(10, principal, null));
        model.addAttribute("listPost", postServiceNew.listarPost(Sort.by(Sort.Direction.DESC, "date")));
        model.addAttribute("comunidades", CommunityServiceNew.listarRandomCommunities(4, principal));
        model.addAttribute("comments", commentsServiceNew.listarComments(Sort.by(Sort.Direction.ASC, "id")));
        model.addAttribute("qtdReaction", reactionServiceNew.countReaction());
        model.addAttribute("book", bookService.getRandomLivro());

        return "/Index";
    }

    @Override
    public String populateModelForProfileView(Model model, Authentication authentication, Principal principal) {
        if (authentication != null && authentication.isAuthenticated()) { // TODO - Usar do UserUtils
            String username = authentication.getName();

            model = userUtils.setModelIfAuthenticationExists(principal, authentication, model);
            model.addAttribute("listPost", postServiceNew.listAllPostsInACommunity(username));
            model.addAttribute("books", bookService.listarRandomLivros(10, principal, null));
            model.addAttribute("comments", commentsServiceNew.listarComments(Sort.by(Sort.Direction.ASC, "id")));
            model.addAttribute("qtdReaction", reactionServiceNew.countReaction());
        }

        return "/Perfil";
    }
}
