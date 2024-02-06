package com.tcc.entrepaginas.modules.community.usecases.posts.createpost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.entrepaginas.modules.community.entities.Post;
import com.tcc.entrepaginas.modules.community.service.PostService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/posts")
public class CreatePostController {

    @Autowired
    private PostService postService;

    @PostMapping("/create/{communityId}/{userId}")
    public String createPost(@Valid Post post, @PathVariable String communityId,
            @PathVariable("userId") String usuarioId, @RequestParam("imagem") MultipartFile image,
            BindingResult result, RedirectAttributes attributes, Model model, HttpServletRequest request) {

        postService.createPost(communityId, usuarioId, image, post, request);

        return "redirect:/community/" + communityId;
    }

}
