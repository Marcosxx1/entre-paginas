package com.tcc.entrepaginas.modules.users.usecases.createimage;

import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
@RequestMapping("/user")
public class CreateImageController {

    @Autowired
    public final UsuarioService usuarioService;

    public CreateImageController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/image/{id}")
    public String createImage(
            @PathVariable("id") String idUsuario,
            @RequestParam("imagem") MultipartFile image,
            HttpServletRequest request,
            Model model,
            RedirectAttributes attributes) {

        Usuario usuario = usuarioService.pegarUsuario(idUsuario);

        String fileName = usuarioService.atualizarImagemUsuario(image);
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        String url = baseUrl + "/uploads/" + fileName;

        usuario.setImagem(url);

        usuarioService.salvarUsuario(usuario);

        return "redirect:/perfil";
    }
}
