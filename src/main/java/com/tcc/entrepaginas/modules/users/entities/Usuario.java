package com.tcc.entrepaginas.modules.users.entities;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tcc.entrepaginas.modules.community.entities.Membros;
import com.tcc.entrepaginas.modules.community.entities.Post;
import com.tcc.entrepaginas.modules.books.entities.Livro;
import com.tcc.entrepaginas.modules.community.entities.Comments;
import com.tcc.entrepaginas.modules.community.entities.SubComments;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String imagem;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Email(message = "O email deve ser válido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "O login é obrigatório")
    @Column(unique = true)
    private String login;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    @Column(columnDefinition = "boolean default false")
    private boolean premium;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Instant dataNascimento;

    @ManyToOne
    @JoinColumn(name = "papel_id")
    private Papel papel;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubComments> subComments;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Membros> membros;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livro> livros;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comentarios;

    public Usuario() {
    }

    public Usuario(String nome, String email, String login, String senha,
            boolean premium, Instant dataNascimento, Papel papel) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.premium = premium;
        this.dataNascimento = dataNascimento;
        this.papel = papel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public Instant getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public List<Membros> getMembros() {
        return membros;
    }

    public void setMembros(List<Membros> membros) {
        this.membros = membros;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comments> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comments> comentarios) {
        this.comentarios = comentarios;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public List<SubComments> getSubComments() {
        return subComments;
    }

    public void setSubComments(List<SubComments> subComments) {
        this.subComments = subComments;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.papel.getPapelNome().equals("ADMIN")) {
            return List.of(new SimpleGrantedAuthority("ADMIN"),
                    new SimpleGrantedAuthority("USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("USER"));
        }
    }

    @Override
    public String getPassword() {
        return this.senha;

    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
