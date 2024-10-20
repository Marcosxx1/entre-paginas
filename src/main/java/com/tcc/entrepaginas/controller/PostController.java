package com.tcc.entrepaginas.controller;

import com.tcc.entrepaginas.domain.dto.NovoPostRequest;
import com.tcc.entrepaginas.service.comments.CommentsServiceNew;
import com.tcc.entrepaginas.service.post.PostServiceNew;
import com.tcc.entrepaginas.service.reaction.ReactionServiceNew;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final CommentsServiceNew commentsServiceNew;

    @PostMapping("/create/{communityId}/{userId}")
    public String createPost(
            @Valid NovoPostRequest novoPostRequest,
            @PathVariable String communityId,
            @PathVariable("userId") String usuarioId,
            @RequestParam("imagem") MultipartFile image,
            HttpServletRequest request) {

        log.info(
                "PostController - POST on /create/{}/{}; NovoPostRequest: {}, called by userId: {}",
                communityId,
                usuarioId,
                novoPostRequest,
                usuarioId);

        return postServiceNew.createPost(communityId, usuarioId, image, novoPostRequest, request);
    }

    @PostMapping("/likes/{idPost}")
    // Autenticados podem votar
    public ResponseEntity<?> likesPost(@PathVariable("idPost") String idPost, Authentication authentication) {

        log.info(
                "PostController - POST on /likes/{idPost}; /likes/{}; called to like post with id: {}", idPost, idPost);

        return reactionServiceNew.reacaoPost(idPost, "like", authentication);
    }

    @DeleteMapping("/delete/{id}")
    public String deletarPost(@PathVariable("id") String idPost, RedirectAttributes attributes, Model model) {

        log.info(
                "PostController - DELETE on /delete/{id}; /delete/{}; called to delete post with id: {}",
                idPost,
                idPost);

        return postServiceNew.apagarPostPorId(idPost);
    }

    /*    @PatchMapping("update/{postId}/{communityId}")
    public String updateCommunity( // Vai retornar para a página de comunidade passando o ID
                                   @PathVariable("postId") String postId,
                                   @PathVariable("communityId") String communityId,
                                   @RequestParam("updatePostRequest") UpdatePostRequest updatePostRequest) {
        return postServiceNew.updatePost(updatePostRequest,  postId,communityId);

    }*/

    @PutMapping("/edit/{commentId}")
    public ResponseEntity<?> editComment(@PathVariable String commentId, @RequestBody Map<String, String> payload) {

        String newContent = payload.get("content");
        log.info("PostController - PUT on/edit/{commentId} /edit/{}; new content: {}", commentId, newContent);

        boolean updated = commentsServiceNew.editComment(commentId, newContent);
        if (updated) {
            log.info("PostController - Comment with id {} updated successfully.", commentId);
            return ResponseEntity.ok("Comment updated successfully.");
        } else {
            log.warn("PostController - Comment with id {} not found.", commentId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found.");
        }
    }
}
