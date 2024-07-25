package com.tcc.entrepaginas.modules.users.usecases.updateuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.domain.Usuario;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class AtualizarUsuarioController {

  @Autowired
  private final UsuarioService usuarioService;

  public AtualizarUsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @PostMapping("/edit/{id}")
  public String atualizarUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult result,
      @PathVariable String id, Model model) {

    if (result.hasErrors()) {
      return "redirect:/infos/" + usuario.getId();
    }

    Usuario userEdited = null;
    try {
      userEdited = usuarioService.pegarUsuario(id);

      if (usuario.getSenha() != null) {
        String newSenha = usuario.getSenha();
        if (newSenha != null && !newSenha.isEmpty()) {
          BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
          String encodedPassword = passwordEncoder.encode(newSenha);
          userEdited.setSenha(encodedPassword); // Aqui
        }
      }

      userEdited.setNome(usuario.getNome());
      userEdited.setEmail(usuario.getEmail());
      userEdited.setLogin(usuario.getLogin());

      usuarioService.salvarUsuario(userEdited);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFound(id);
    }

    return "redirect:/perfil";
  }
}
