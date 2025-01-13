package com.tcc.entrepaginas.service.index;

import com.tcc.entrepaginas.domain.entity.Community;
import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.mapper.user.UserMapper;
import com.tcc.entrepaginas.repository.ReactionRepository;
import com.tcc.entrepaginas.service.book.BookService;
import com.tcc.entrepaginas.service.comments.CommentsServiceNew;
import com.tcc.entrepaginas.service.community.CommunityServiceNew;
import com.tcc.entrepaginas.service.enums.EnumListingService;
import com.tcc.entrepaginas.service.post.PostServiceNew;
import com.tcc.entrepaginas.service.user.UserService;
import com.tcc.entrepaginas.utils.user.UserUtils;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
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
    private final CommunityServiceNew communityServiceNew;
    private final EnumListingService enumListingService;
    private final ReactionRepository reactionRepository;

    @Override
    public String redirecctToIndexOrLoginBasedOnAuth(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/index";
        }
        return "Login"; // Antes: /Login, depois: Login  .
    }

    @Override
    public String populateIndexModel(Model model, Principal principal, Authentication authentication) {
        model = userUtils.setModelIfAuthenticationExists(authentication, model);

        model.addAttribute("books", bookService.listarRandomLivros(10, principal, null));
        model.addAttribute("listPost", postServiceNew.listarPost(Sort.by(Sort.Direction.DESC, "date")));
        model.addAttribute("comunidades", communityServiceNew.listarRandomCommunities(4, principal));
        model.addAttribute("comments", commentsServiceNew.listarComments(Sort.by(Sort.Direction.ASC, "id")));
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());

        Map<String, Integer> reaction = new HashMap<>();

        for (Community comunidade : communityServiceNew.listarCommunities(Sort.by(Sort.Direction.ASC, "id"))) {
            for (Post post : comunidade.getPost()) {
                reaction.put(post.getId(), reactionRepository.countByReacao(post.getId(), "like"));
            }
        }

        model.addAttribute("qtdReaction", reaction);
        model.addAttribute("book", bookService.getRandomLivro(authentication));

        return "Index";
    }

    @Override
    public String populateModelForProfileView(Model model, Authentication authentication, Principal principal) {
        if (authentication != null && authentication.isAuthenticated()) { // Usar do UserUtils
            String username = authentication.getName();

            model = userUtils.setModelIfAuthenticationExists(authentication, model);
            model.addAttribute("listPost", postServiceNew.listAllPostsInACommunity(username));
            model.addAttribute("books", bookService.listarRandomLivros(10, principal, null));
            model.addAttribute("comments", commentsServiceNew.listarComments(Sort.by(Sort.Direction.ASC, "id")));
        }

        return "Perfil";
    }

    @Override
    public String prepareUser(Model model, Authentication authentication) {

        var updateUserNameLoginAndEmailRequest = userMapper.toUpdateUserNameLoginAndEmailRequest(authentication);

        model = userUtils.setModelIfAuthenticationExists(authentication, model);
        model.addAttribute("updateUserNameLoginAndEmailRequest", updateUserNameLoginAndEmailRequest);
        model.addAttribute("estadosBrasil", enumListingService.listarTodosEstadosBrasil());

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

        return "Perfil";
    }

    @Override
    public String suporte(Model model, Principal principal, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) { // Usar do UserUtils
            String username = authentication.getName();

            model = userUtils.setModelIfAuthenticationExists(authentication, model);
            model.addAttribute("listPost", postServiceNew.listAllPostsInACommunity(username));
            model.addAttribute("books", bookService.listarRandomLivros(10, principal, null));
            model.addAttribute("comments", commentsServiceNew.listarComments(Sort.by(Sort.Direction.ASC, "id")));
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
            model.addAttribute("communities", communityServiceNew.listarCommunities(Sort.by(Sort.Direction.ASC, "id")));
        }

        return "TelaAdmin";
    }

    @Override
    public String filter(Model model, Principal principal, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Usuario user = userUtils.getUserByLogin(authentication.getName());

            userUtils.setModelIfAuthenticationExists(authentication, model);
            model.addAttribute("LivosDaRegiao", bookService.trocaDeLivroPorRegiao(model, authentication));
            model.addAttribute("todosOsLivros", bookService.listarTodasTrocas(authentication));
        }
        return "Filter";
    }
}
