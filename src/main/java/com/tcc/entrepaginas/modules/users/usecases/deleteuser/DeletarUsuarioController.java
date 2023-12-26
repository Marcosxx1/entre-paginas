package com.tcc.entrepaginas.modules.users.usecases.deleteuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.entrepaginas.modules.users.service.UsuarioService;

@Controller
@RequestMapping("/user")
public class DeletarUsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  public DeletarUsuarioController(UsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @GetMapping("/delete/{id}")
  public String deletarUsuario(@PathVariable("id") String id,
      RedirectAttributes redirectAttributes) {

    usuarioService.apagarUsuarioPorId(id);

    SecurityContextHolder.getContext().setAuthentication(null);

    return "redirect:/index";
  }
}
