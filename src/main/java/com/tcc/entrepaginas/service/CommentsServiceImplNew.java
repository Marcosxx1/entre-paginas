package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Comments;
import com.tcc.entrepaginas.repository.CommentsRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentsServiceImplNew implements CommentsServiceNew {

    private final CommentsRepository commentsRepository;

    public List<Comments> listarComments(Sort sort) {
        return commentsRepository.findAll(sort);
    }

    @Override
    public boolean editComment(String commentId, String newContent) {
        Optional<Comments> optionalComment = commentsRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comments comment = optionalComment.get();
            comment.setContent(newContent);
            commentsRepository.save(comment);
            return true;
        }
        return false;
    }
}
