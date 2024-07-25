package com.tcc.entrepaginas.modules.community.usecases.posts.deletepost;

import com.tcc.entrepaginas.repository.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/posts")
public class DeletePostController {

    @Autowired
    private PostService postService;

    @DeleteMapping("/delete/{id}")
    public String deletarPost(@PathVariable("id") String idPost, RedirectAttributes attributes, Model model) {

        postService.apagarPostPorId(idPost);

        return "redirect:/perfil";
    }
}
