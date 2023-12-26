package com.tcc.entrepaginas.modules.community.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.modules.community.entities.Community;
import com.tcc.entrepaginas.modules.community.entities.Post;

public interface PostRepository extends JpaRepository<Post, String>{

    List<Post> findByCommunity(Community community);

}
