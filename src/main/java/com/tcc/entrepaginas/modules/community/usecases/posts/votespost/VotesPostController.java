package com.tcc.entrepaginas.modules.community.usecases.posts.votespost;

import com.tcc.entrepaginas.modules.community.record.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
public class VotesPostController {

    @Autowired
    private VotesPostUseCase votesPostUseCase;

    public VotesPostController(VotesPostUseCase votesPostUseCase) {
        this.votesPostUseCase = votesPostUseCase;
    }

    @PostMapping("/{id}")
    public ResponseEntity<PostDto> votesPost(@RequestParam("id") String id) {
        PostDto postDto = votesPostUseCase.votesPost(id);

        return ResponseEntity.ok(postDto);
    }
}
