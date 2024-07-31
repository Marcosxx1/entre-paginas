package com.tcc.entrepaginas.modules.community.usecases.posts.reactionpost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.entrepaginas.modules.community.service.ReactionService;

@RestController
@RequestMapping("/posts")
public class ReactionPostController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping("/likes/{idPost}")
    public int likesPost(@PathVariable("idPost") String idPost) {
        return reactionService.reacaoPost(idPost, "like");
    }

    @PostMapping("/dislike/{idPost}")
    public int dislikePost(@PathVariable("idPost") String idPost) {
        return reactionService.reacaoPost(idPost, "dislike");
    }
}