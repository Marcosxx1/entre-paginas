package com.tcc.entrepaginas.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleCommunity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String papel;

    @OneToMany(mappedBy = "roleCommunity", cascade = CascadeType.ALL)
    private List<Membros> membros;

    public RoleCommunity(String papelString) {
        this.papel = papelString;
    }
}
