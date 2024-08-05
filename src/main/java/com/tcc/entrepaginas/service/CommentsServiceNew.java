package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Comments;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;

public interface CommentsServiceNew {

    List<Comments> listarComments(Sort sort);

    boolean editComment(String commentId, String newContent);

    String salvarComments(Comments comments, String idPost, Authentication authentication);

    Comments buscarComments(String id);
}
