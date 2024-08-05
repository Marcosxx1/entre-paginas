package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.entity.Comments;
import com.tcc.entrepaginas.service.CommentsServiceNew;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentsController {

    private final CommentsServiceNew commentsServiceNew;

    @PostMapping("/create/save/{idPost}")
    public String createComments(@PathVariable String idPost, @Valid Comments comments, Authentication authentication) {
        log.info(
                "CommentsController - POST on /create/save/{idPost}; /create/save/{}; called by user: {}",
                idPost,
                authentication != null ? authentication.getName() : "Anonymous");
        return commentsServiceNew.salvarComments(comments, idPost, authentication);
    }
}
