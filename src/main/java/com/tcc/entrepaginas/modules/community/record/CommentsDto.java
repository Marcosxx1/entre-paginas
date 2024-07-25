package com.tcc.entrepaginas.modules.community.record;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.tcc.entrepaginas.domain.Comments;

public record CommentsDto(String id, String content, Instant date) {

    public static CommentsDto fromComment(Comments comment) {
        return new CommentsDto(
                comment.getId(),
                comment.getContent(),
                comment.getDate());
    }

    public static List<CommentsDto> transformeCommentsEmDto(List<Comments> comments) {
        List<CommentsDto> commentsDtos = new ArrayList<>();

        for (Comments comment : comments) {
            CommentsDto commentsDto = CommentsDto.fromComment(comment);
            commentsDtos.add(commentsDto);
        }

        return commentsDtos;
    }
}
