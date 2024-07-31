package com.tcc.entrepaginas.domain.entity;

import com.tcc.entrepaginas.domain.enums.ReacaoStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String reacao;

    @Enumerated(EnumType.STRING)
    private ReacaoStatus jaVotou;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


}
