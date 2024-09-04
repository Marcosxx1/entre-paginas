package com.tcc.entrepaginas.service.imagemlivro;

import com.tcc.entrepaginas.domain.entity.ImagemLivro;
import com.tcc.entrepaginas.domain.entity.Livro;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImagemLivroService {

    void saveImagemLivro(ImagemLivro imagemLivro);

    List<ImagemLivro> processImagens(List<MultipartFile> imagens, Livro livro, HttpServletRequest request);

    void init();

    String atualizarImagemLivro(MultipartFile image);
}
