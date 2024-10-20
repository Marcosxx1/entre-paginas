package com.tcc.entrepaginas.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tbUsuario") // define o nome da tabela no banco de dados
@AllArgsConstructor // Cria um construtor com todos os argumentos
@NoArgsConstructor // cria um costrutor vazio
@Getter // cria tdos os getter()
@Setter // cria todos os setter()
public class TbUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TbContato> contatos;


}
