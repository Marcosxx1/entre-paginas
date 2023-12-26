package com.tcc.entrepaginas.modules.community.usecases.posts.updatepost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tcc.entrepaginas.modules.community.record.PostDto;

@RestController
@RequestMapping("/api/posts")
public class UpdatePostController {

    @Autowired
    private UpdatePostUseCase updatePostUseCase;

    public UpdatePostController(UpdatePostUseCase updatePostUseCase) {
        this.updatePostUseCase = updatePostUseCase;
    }

    @PatchMapping("/{communityId}/{userId}")
    public ResponseEntity<PostDto> updateCommunity(@PathVariable("communityId") String communityId,
            @PathVariable("userId") String usuarioId, @RequestParam("postDto") String postDtoJson,
            @RequestParam("image") MultipartFile image) {
        PostDto updatePost = updatePostUseCase.updatePost(postDtoJson, usuarioId, communityId, image);
        if (updatePost != null) {
            return ResponseEntity.ok(updatePost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
