package com.tcc.entrepaginas.modules.community.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.modules.community.entities.ImagemPost;

public interface ImagemPostRepository extends JpaRepository<ImagemPost, String> {
    
}
