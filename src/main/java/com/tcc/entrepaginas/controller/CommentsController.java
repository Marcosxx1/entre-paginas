package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.entity.Comments;
import com.tcc.entrepaginas.service.CommentsServiceNew;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsServiceNew commentsServiceNew;

    @PostMapping("/create/save/{idPost}/{userLogin}")
    public String createComments(
            @PathVariable String idPost, @PathVariable String userLogin, @Valid Comments comments) {

        return commentsServiceNew.salvarComments(comments, idPost, userLogin);
    }
}
