package com.tcc.entrepaginas.service.comments;

import com.tcc.entrepaginas.domain.entity.Comments;
import com.tcc.entrepaginas.domain.entity.Post;
import com.tcc.entrepaginas.domain.entity.Usuario;
import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.repository.CommentsRepository;
import com.tcc.entrepaginas.service.post.PostServiceNew;
import com.tcc.entrepaginas.utils.user.UserUtils;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentsServiceImplNew implements CommentsServiceNew {

    private final CommentsRepository commentsRepository;
    private PostServiceNew postService;
    private final UserUtils userUtils;

    public List<Comments> listarComments(Sort sort) {
        return commentsRepository.findAll(sort);
    }

    @Override
    public boolean editComment(String commentId, String newContent) {
        Optional<Comments> optionalComment = commentsRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comments comment = optionalComment.get();
            comment.setContent(newContent);
            commentsRepository.save(comment);
            return true;
        }
        return false;
    }

    @Override
    public String salvarComments(Comments comments, String idPost, Authentication authentication) {

        Post post = postService.buscarPost(idPost);
        Usuario usuario = userUtils.getUserByLogin(authentication.getName());

        comments.setPost(post);
        comments.setUsuario(usuario);

        commentsRepository.save(comments);

        return "redirect:/index";
    }

    @Override
    public Comments buscarComments(String id) {
        return commentsRepository.findById(id).orElseThrow(() -> new ResourceNotFound(id));
    }
}
