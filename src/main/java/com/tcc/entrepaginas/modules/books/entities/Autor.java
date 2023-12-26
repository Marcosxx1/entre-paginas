// package com.tcc.entre_paginasmarcketplace_de_livrosbackend.modules.books.entities;

// import java.util.List;

// import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.ManyToMany;

// @Entity
// public class Autor {

//     @Id
//     @GeneratedValue(strategy = GenerationType.UUID)
//     private String id;

//     private String nome;

//     @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
//     private List<Livro> livros;

//     public Autor() {
//     }

//     public Autor(String nome) {
//         this.nome = nome;
//     }

//     public String getId() {
//         return id;
//     }

//     public void setId(String id) {
//         this.id = id;
//     }

//     public String getNome() {
//         return nome;
//     }

//     public void setNome(String nome) {
//         this.nome = nome;
//     }

//     public List<Livro> getLivros() {
//         return livros;
//     }

//     public void setLivros(List<Livro> livros) {
//         this.livros = livros;
//     }

// }
