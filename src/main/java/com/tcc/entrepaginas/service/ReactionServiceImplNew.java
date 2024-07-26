package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.repository.ReactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReactionServiceImplNew implements ReactionServiceNew {

    private final ReactionRepository reactionRepository;

    public int countReaction() {
        int likesCount = reactionRepository.countByReacao("like");
        int dislikesCount = reactionRepository.countByReacao("dislike");

        return likesCount - dislikesCount;
    }
}
