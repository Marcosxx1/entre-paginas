package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.NovoPostRequest;
import com.tcc.entrepaginas.service.PostServiceNew;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
@Slf4j
public class PostController {

    private final PostServiceNew postServiceNew;

    @PostMapping("/create/{communityId}/{userId}")
    public String createPost(
            @Valid NovoPostRequest novoPostRequest,
            @PathVariable String communityId,
            @PathVariable("userId") String usuarioId,
            @RequestParam("imagem") MultipartFile image,
            HttpServletRequest request) {

        return postServiceNew.createPost(communityId, usuarioId, image, novoPostRequest, request);
    }
}
