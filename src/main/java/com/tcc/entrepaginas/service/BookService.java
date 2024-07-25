package com.tcc.entrepaginas.service;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

public interface BookService {

    String beginBookCreation(Model model, String idUsuario, Authentication authentication );
    /*
            @PathVariable("id") String idUsuario,
            @Valid Livro livro,
            @RequestParam("images") List<MultipartFile> imagens,
            HttpServletRequest request*/
    //String saveBook(String id, )
}
