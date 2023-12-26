package com.tcc.entrepaginas.modules.community.usecases.posts.listpost;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.entrepaginas.modules.community.entities.Post;
import com.tcc.entrepaginas.modules.community.service.PostService;

@RestController
@RequestMapping("/posts")
public class ListPostController {

    @Autowired
    private PostService postService;

    @GetMapping("/list")
    public String listPosts(Model model) {
        List<Post> posts = postService.listarPost(Sort.by(Sort.Direction.ASC, "id"));

        model.addAttribute("posts", posts);
        return "/Alguma Coisa";
    }
}