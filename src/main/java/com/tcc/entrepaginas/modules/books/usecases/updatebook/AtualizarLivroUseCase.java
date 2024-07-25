// package com.tcc.entrepaginas.modules.books.usecases.updatebook;

// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.tcc.entrepaginas.exceptions.ResourceNotFound;
// import com.tcc.entrepaginas.modules.books.entities.Livro;
// import com.tcc.entrepaginas.modules.books.record.BookDto;
// import com.tcc.entrepaginas.modules.books.repositories.LivroRepository;
// import com.tcc.entrepaginas.domain.Usuario;
// import com.tcc.entrepaginas.repository.UsuarioRepository;

// import jakarta.persistence.EntityNotFoundException;

// @Service
// public class AtualizarLivroUseCase {

//     @Autowired
//     private final LivroRepository livroRepository;

//     @Autowired
//     private final UsuarioRepository usuarioRepository;

//     public AtualizarLivroUseCase(LivroRepository livroRepository,
//             UsuarioRepository usuarioRepository) {
//         this.livroRepository = livroRepository;
//         this.usuarioRepository = usuarioRepository;
//     }

//     public BookDto atualizarLivro(String id, BookDto livroDTO) {
//          if (livroDTO == null) {
//             throw new RuntimeException("livro inválido");
//         }

//         try {
//             Livro livro = this.pegarLivro(id);

//             livro.setNome(livroDTO.nome());
//             livro.setDescricao(livroDTO.descricao());
//             livro.setEditora(livroDTO.editora());
//             livro.setEstado(livroDTO.estado());
//             livro.setTipo(livroDTO.tipo());
//             livro.setCategoria(livroDTO.categoria());
//             livro.setPreco(livroDTO.preco());
//             livro.setUsuario(pegarUsuario(livroDTO.userDto().id())); 

//             livroRepository.save(livro);

//         } catch (EntityNotFoundException e) {
//             throw new ResourceNotFound(id);
//         }

//         return livroDTO;
//     }

//     public Livro pegarLivro(String id) {
//          Optional<Livro> livro = livroRepository.findById(id);
//          return livro.orElseThrow(() -> new ResourceNotFound(id));
//     }

//     public Usuario pegarUsuario(String id) {
//         Optional<Usuario> usuario = usuarioRepository.findById(id);
//          return usuario.orElseThrow(() -> new ResourceNotFound(id));
//     }
// }