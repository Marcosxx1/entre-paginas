package com.tcc.entrepaginas.service;

import com.tcc.entrepaginas.domain.dto.NovoLivroRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {

    String beginBookCreation(Model model, String idUsuario, Authentication authentication );

   String saveBook(String idUsuario, NovoLivroRequest novoLivroRequest, List<MultipartFile> imagens, HttpServletRequest request);

}
