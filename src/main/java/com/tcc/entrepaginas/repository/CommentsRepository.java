package com.tcc.entrepaginas.repository;

import com.tcc.entrepaginas.domain.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, String> {}
