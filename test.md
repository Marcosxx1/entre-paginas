```java
package com.tcc.entrepaginas.modules.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.books.entities.enums.ReacaoStatus;
import com.tcc.entrepaginas.modules.community.entities.Post;
import com.tcc.entrepaginas.modules.community.entities.Reaction;
import com.tcc.entrepaginas.modules.community.repositories.ReactionRepository;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UsuarioService usuarioService;

    public Reaction buscarReaction(String id) {
        Optional<Reaction> reaction = reactionRepository.findById(id);
        return reaction.orElseThrow(() -> new ResourceNotFound(id));
    }

    public List<Reaction> listarReactions(Sort sort) {
        return reactionRepository.findAll(sort);
    }

    public int countReaction() {
        int likesCount = reactionRepository.countByReacao("like");
        int dislikesCount = reactionRepository.countByReacao("dislike");

        return likesCount - dislikesCount;
    }

    public void apagarPostPorId(String id) {
        Reaction reaction = this.buscarReaction(id);
        reactionRepository.delete(reaction);
    }

    public int reacaoPost(String idPost, String reacao, String idUsuario) {
        Post post = postService.buscarPost(idPost);
        Usuario usuario = usuarioService.pegarUsuario(idUsuario);

        if (usuario == null) {
            throw new IllegalStateException("Usuário não existe.");
        }
        if (post == null) {
            throw new IllegalArgumentException("Post not found.");
        }

        // Verificar se o usuário já votou neste post
        if (usuarioJaVotou(idPost, idUsuario)) {
            throw new IllegalStateException("User has already voted on this post.");
        }

        // Buscar a reação existente para o post
        Reaction existingReaction = reactionRepository.findByPostAndUsuario(post, usuario).orElse(null);

        // Atualizar ou criar uma nova reação com base no voto do usuário
        if (existingReaction == null) {
            // Se não houver reação existente, criar uma nova
            existingReaction = new Reaction();
            existingReaction.setUsuario(usuario);
            existingReaction.setPost(post);
            existingReaction.setJaVotou(ReacaoStatus.NENHUM);
        }

        // Processar o voto do usuário
        if (reacao.equals("like")) {
            if (existingReaction.getJaVotou() == ReacaoStatus.LIKE) {
                // Se o usuário já deu like antes, lançar exceção
                throw new IllegalStateException("User has already liked this post.");
            } else {
                // Caso contrário, definir a reação como like
                existingReaction.setJaVotou(ReacaoStatus.LIKE);
            }
        } else if (reacao.equals("dislike")) {
            if (existingReaction.getJaVotou() == ReacaoStatus.LIKE) {
                // Se o usuário já deu like antes, subtrair
                // Implemente a lógica para subtrair o like e adicionar o dislike
                // Não forneci a lógica específica aqui porque depende de como o sistema
                // armazena e manipula os contadores de likes e dislikes.
            } else {
                // Caso contrário, definir a reação como dislike
                existingReaction.setJaVotou(ReacaoStatus.DISLIKE);
            }
        }

        // Salvar a reação atualizada ou criada
        reactionRepository.save(existingReaction);

        // Retornar a contagem atualizada de reações
        return this.countReaction();
    }

    public boolean usuarioJaVotou(String idPost, String idUsuario) {
        Post post = postService.buscarPost(idPost);
        Usuario usuario = usuarioService.pegarUsuario(idUsuario);

        Optional<Reaction> existingReaction = reactionRepository.findByPostAndUsuario(post, usuario);

        return existingReaction.isPresent() && existingReaction.get().getJaVotou() != ReacaoStatus.NENHUM;
    }

}


```


```java 

//primeira implementação:
package com.tcc.entrepaginas.modules.community.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tcc.entrepaginas.exceptions.ResourceNotFound;
import com.tcc.entrepaginas.modules.community.entities.Post;
import com.tcc.entrepaginas.modules.community.entities.Reaction;
import com.tcc.entrepaginas.modules.community.repositories.ReactionRepository;
import com.tcc.entrepaginas.modules.users.entities.Usuario;
import com.tcc.entrepaginas.modules.users.service.UsuarioService;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UsuarioService usuarioService;

    public Reaction buscarReaction(String id) {
        Optional<Reaction> reaction = reactionRepository.findById(id);
        return reaction.orElseThrow(() -> new ResourceNotFound(id));
    }

    public List<Reaction> listarReactions(Sort sort) {
        return reactionRepository.findAll(sort);
    }

    public int countReaction() {
        int likesCount = reactionRepository.countByReacao("like");
        int dislikesCount = reactionRepository.countByReacao("dislike");

        return likesCount - dislikesCount;
    }

    public void apagarPostPorId(String id) {
        Reaction reaction = this.buscarReaction(id);
        reactionRepository.delete(reaction);
    }

    public int reacaoPost(String idPost, String reacao, String idUsuario) {
        Post post = postService.buscarPost(idPost);

        Usuario usuarioEncontrado = usuarioService.pegarUsuario(idUsuario);

        if (usuarioEncontrado == null) {
            throw new IllegalStateException("Usuário não existe.");
        }
        if (post == null) {
            throw new IllegalArgumentException("Post not found.");
        }

        /*
         * if (usuarioJaVotou(idPost, idUsuario)) {
         * throw new IllegalStateException("User has already voted on this post.");
         * }
         */
        Reaction reaction = new Reaction(reacao, usuarioEncontrado, post);

        reactionRepository.save(reaction);

        return this.countReaction();
    }

    public boolean usuarioJaVotou(String idPost, String idUsuario) {
        List<Reaction> reactions = listarReactions(Sort.by(Sort.Direction.ASC, "id"));

        for (Reaction reaction : reactions) {
            if (reaction.getPost().getId().equals(idPost) && reaction.getUsuario().getId().equals(idUsuario)) {
                System.out.println("Usuário já votou.");
                return true;
            }
        }

        return false;
    }

}

```