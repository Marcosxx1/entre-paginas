package com.tcc.entrepaginas.modules.users.usecases.createuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.entrepaginas.modules.users.entities.Papel;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.repositories.PapelRepository;
import com.tcc.entrepaginas.modules.users.repositories.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class CriarUsuarioController {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PapelRepository papelRepository;

  @GetMapping("/register")
  public String register(Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
    if (authentication != null && authentication.isAuthenticated()) {
      return "redirect:/index";
    }
    model.addAttribute("usuario", new Usuario());
    return "CadastrarUsuario";
  }

  @PostMapping("/register/save")
  public String createUser(@Valid Usuario usuario, BindingResult result,
      RedirectAttributes attributes, Model model) {

    if (result.hasErrors()) {
      return "CadastrarUsuario";
    }

    if (this.usuarioRepository.findByLogin(usuario.getLogin()) != null) {
      model.addAttribute("loginExiste", "User already exists with this login");
      return "CadastrarUsuario";
    }

    Papel papel = papelRepository.findByPapelNome("USER");

    usuario.setPapel(papel);
    usuario.setSenha(this.senhaCriptografada(usuario));

    usuarioRepository.save(usuario);

    attributes.addFlashAttribute("mensagem", "Registration completed successfully");
    return "redirect:/login";
  }

  public String senhaCriptografada(Usuario usuario) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.encode(usuario.getSenha());
  }
}
