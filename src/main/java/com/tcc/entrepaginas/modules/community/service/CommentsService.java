package com.tcc.entrepaginas.modules.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.community.entities.Comments;
import com.tcc.entrepaginas.modules.community.entities.Post;
import com.tcc.entrepaginas.modules.community.repositories.CommentsRepository;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UsuarioService userService;

    public Comments buscarComments(String id) {
        Optional<Comments> comments = commentsRepository.findById(id);
        return comments.orElseThrow(() -> new ResourceNotFound(id));
    }

    public List<Comments> listarComments(Sort sort) {
        return commentsRepository.findAll(sort);
    }

    public void apagarCommentsPorId(String id) {
        Comments comments = this.buscarComments(id);
        commentsRepository.delete(comments);
    }

    public void salvarComments(Comments comments, String idPost, String userLogin) {
        Post post = postService.buscarPost(idPost);
        Usuario usuario = userService.pegarUsuarioPorLogin(userLogin);

        comments.setPost(post);
        comments.setUsuario(usuario);

        commentsRepository.save(comments);
    }
}
