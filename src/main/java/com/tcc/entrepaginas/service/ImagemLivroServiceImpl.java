package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.entity.ImagemLivro;
import com.tcc.entrepaginas.repository.ImagemLivroRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ImagemLivroServiceImpl implements ImagemLivroService {

    private final ImagemLivroRepository imagemLivroRepository;

    @Override
    public void saveImagemLivro(ImagemLivro imagemLivro) {
        imagemLivroRepository.save(imagemLivro);
    }
}
