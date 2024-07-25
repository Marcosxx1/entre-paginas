package com.tcc.entrepaginas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.entrepaginas.domain.ImagemPost;

public interface ImagemPostRepository extends JpaRepository<ImagemPost, String> {
    
}
