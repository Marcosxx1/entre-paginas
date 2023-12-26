package com.tcc.entrepaginas.modules.users.usecases.listusers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;

@RestController
@RequestMapping("/user")
public class ListarTodosUsuariosController {

  @Autowired
  private UsuarioService usuarioService;

  @GetMapping("/list")
  @ResponseBody
  public List<String> listarUsuarios(@RequestParam(value = "query", required = false) String query,
      @RequestParam(value = "option", required = false) String option) {
    List<Usuario> usuarios;

    if (query != null && !query.isEmpty() && option != null && !option.isEmpty()) {

      usuarios = usuarioService.buscarUsuarios(query, option);
    } else {
      usuarios = usuarioService.listarUsuarios(Sort.by(Sort.Direction.ASC, "id"));
    }

    List<String> resultados = new ArrayList<>();
    for (Usuario usuario : usuarios) {
      resultados.add(usuario.getNome());
    }

    return resultados;
  }

}
