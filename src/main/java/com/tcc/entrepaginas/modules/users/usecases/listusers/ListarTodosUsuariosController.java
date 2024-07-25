package com.tcc.entrepaginas.modules.users.usecases.listusers;

import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.modules.users.record.UserDto;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class ListarTodosUsuariosController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/list")
    public List<UserDto> listarUsuarios(@RequestParam(required = false) String query) {
        List<Usuario> usuarios;

        if (query != null && !query.isEmpty()) {
            usuarios = usuarioService.buscarUsuarios(query);
        } else {
            usuarios = usuarioService.listarUsuarios(Sort.by(Sort.Direction.ASC, "id"));
        }

        List<UserDto> resultados = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            UserDto userDTO = UserDto.fromUsuario(usuario);
            resultados.add(userDTO);
        }

        return resultados;
    }
}
