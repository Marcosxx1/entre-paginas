package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.NovoPostRequest;
import com.tcc.entrepaginas.service.PostServiceNew;
import com.tcc.entrepaginas.service.ReactionServiceNew;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
@Slf4j
public class PostController {

    private final PostServiceNew postServiceNew;
    private final ReactionServiceNew reactionServiceNew;

    @PostMapping("/create/{communityId}/{userId}")
    public String createPost(
            @Valid NovoPostRequest novoPostRequest,
            @PathVariable String communityId,
            @PathVariable("userId") String usuarioId,
            @RequestParam("imagem") MultipartFile image,
            HttpServletRequest request) {

        return postServiceNew.createPost(communityId, usuarioId, image, novoPostRequest, request);
    }

    @PostMapping("/likes/{idPost}")
    public ResponseEntity<?> likesPost(@PathVariable("idPost") String idPost) {
        return reactionServiceNew.reacaoPost(idPost, "like");
    }

    @DeleteMapping("/delete/{id}")
    public String deletarPost(@PathVariable("id") String idPost, RedirectAttributes attributes, Model model) {

        return postServiceNew.apagarPostPorId(idPost);
    }
}
