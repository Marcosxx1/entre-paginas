package com.tcc.entrepaginas.modules.community.service;

import java.util.List;
import java.util.Optional;

import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Reaction;
import com.tcc.entrepaginas.repository.ReactionRepository;
import com.tcc.entrepaginas.service.PostServiceNew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tcc.entrepaginas.exceptions.ResourceNotFound;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostServiceNew postService;

    public Reaction buscarReaction(String id) {
        Optional<Reaction> reaction = reactionRepository.findById(id);
        return reaction.orElseThrow(() -> new ResourceNotFound(id));
    }

    public List<Reaction> listarReactions(Sort sort) {
        return reactionRepository.findAll(sort);
    }

    public int countReaction() {
        int likesCount = reactionRepository.countByReacao("like");
        int dislikesCount = reactionRepository.countByReacao("dislike");

        return likesCount - dislikesCount;
    }

    public void apagarPostPorId(String id) {
        Reaction reaction = this.buscarReaction(id);
        reactionRepository.delete(reaction);
    }

    public int reacaoPost(String idPost, String reacao) {
        Post post = postService.buscarPost(idPost);

        if (post == null) {
            throw new IllegalArgumentException("Post not found.");
        }

        if (usuarioJaVotou(idPost, post.getUsuario().getId())) {
            throw new IllegalStateException("User has already voted on this post.");
        }

        Reaction reaction =Reaction.builder().reacao(reacao).usuario(post.getUsuario()).post(post).build();

        reactionRepository.save(reaction);

        return this.countReaction();
    }

    public boolean usuarioJaVotou(String idPost, String idUsuario) {

        Reaction reaction = reactionRepository.findByPostIdAndUsuarioId(idPost, idUsuario);

        return reaction != null;
    }

}