package com.tcc.entrepaginas.modules.community.usecases.comments.createcomments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.entrepaginas.domain.Comments;
import com.tcc.entrepaginas.repository.CommentsService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/comments")
public class CreateComments {

    @Autowired
    private CommentsService commentsService;

    @PostMapping("/create/save/{idPost}/{userLogin}")
    public String createComments(@PathVariable String idPost,
            @PathVariable String userLogin, @Valid Comments comments,
            BindingResult result, RedirectAttributes attributes, Model model) {

        commentsService.salvarComments(comments, idPost, userLogin);

        return "redirect:/index";
    }

}