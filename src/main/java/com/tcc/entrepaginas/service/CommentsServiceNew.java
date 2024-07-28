package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.Comments;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface CommentsServiceNew {

    List<Comments> listarComments(Sort sort);
}
