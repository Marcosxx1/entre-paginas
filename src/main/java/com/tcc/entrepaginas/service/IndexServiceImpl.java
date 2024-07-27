package com.tcc.entrepaginas.service;

import java.security.Principal;

import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.tcc.entrepaginas.mapper.user.UserMapper;
import com.tcc.entrepaginas.utils.UserUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final UserUtils userUtils;
    private final UserMapper userMapper;
    private final BookService bookService;
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
        model.addAttribute("book", bookService.getRandomLivro());

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
}