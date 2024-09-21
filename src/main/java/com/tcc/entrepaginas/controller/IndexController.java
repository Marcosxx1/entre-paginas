package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.service.index.IndexService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final IndexService indexService;

    @GetMapping("/login")
    public String login(Authentication authentication) {
        log.info(
                "IndexController - GET on /login; called by user: {}",
                authentication != null ? authentication.getName() : "Anonymous");
        return indexService.redirecctToIndexOrLoginBasedOnAuth(authentication);
    }

    @GetMapping("/index")
    public String index(Model model, Principal principal, Authentication authentication) {
        log.info(
                "IndexController - GET on /index; called by user: {}",
                principal != null ? principal.getName() : "Anonymous");
        return indexService.populateIndexModel(model, principal, authentication);
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication, Principal principal) {
        log.info(
                "IndexController - GET on /perfil; called by user: {}",
                principal != null ? principal.getName() : "Anonymous");
        return indexService.populateModelForProfileView(model, authentication, principal);
    }

    @GetMapping("/infos")
    public String informacoesDoUsuario(Model model, Authentication authentication) {
        log.info(
                "IndexController - GET on /infos; called by user: {}",
                authentication != null ? authentication.getName() : "Anonymous");
        return indexService.prepareUser(model, authentication);
    }

    @GetMapping("/perfilVisitante/{userName}")
    public String perfilVisitante(
            Model model, @PathVariable("userName") String userName, Authentication authentication) {
        log.info("IndexController - GET on /perfilVisitante/{}; called to view profile of user", userName);
        return indexService.visitOtherUsersFromIndex(model, userName, authentication);
    }

    @GetMapping("/suporte")
    public String suporte(Model model, Principal principal, Authentication authentication) {
        return indexService.suporte(model, principal, authentication);
    }

    @GetMapping("/admin")
    public String telaAdmin(Model model, Principal principal, Authentication authentication) {
        return indexService.telaAdmin(model, principal, authentication);
    }

    @GetMapping("/filter")
    public String getMethodName(Model model, Principal principal, Authentication authentication) {
        return indexService.filter(model, principal, authentication);
    }

    /*
     * TODO - Atualmente no método acima, caso o banco seja novo estamos mandando
     * objetos não checados (null)
     *
     * @GetMapping("/perfilVisitante/{userName}")
     * public String perfilVisitante(Model model, @PathVariable("userName") String
     * userName) {
     * Optional<Usuario> userOptional = userService.getUserByLogin(userName);
     *
     * if (userOptional.isPresent()) {
     * return indexService.prepareProfileView(model, userOptional.get());
     * } else {
     * return "redirect:/error/404";
     * }
     *
     * Por exemplo, tive que adicionar isso no Index:
     *
     * <div th:if="${book.imagens != null}">
     * <img th:src="${book.imagens[0].url}" alt="icone da livro" id="modalBookImage"
     * />
     * </div>
     * <p th:text="${book.imagens != null ? book.imagens[0].nome : 'N/A'}"
     * id="modalBookImageName"></p>
     *
     * Mas não me parece adequado tratar no front a falta de algo na base de dados
     * }
     */
}
