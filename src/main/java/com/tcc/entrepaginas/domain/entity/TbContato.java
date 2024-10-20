package com.tcc.entrepaginas.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbContato") // define o nome da tabela no banco de dados
@AllArgsConstructor // Cria um construtor com todos os argumentos
@NoArgsConstructor // cria um costrutor vazio
@Getter // cria tdos os getter()
@Setter // cria todos os setter()
public class TbContato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String telefone;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private TbUsuario usuario;
}
