package com.tcc.entrepaginas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.domain.Community;
import com.tcc.entrepaginas.domain.Post;

public interface PostRepository extends JpaRepository<Post, String>{

    List<Post> findByCommunity(Community community);

}
