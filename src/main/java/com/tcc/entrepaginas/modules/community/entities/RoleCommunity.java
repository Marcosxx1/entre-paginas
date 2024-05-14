package com.tcc.entrepaginas.modules.community.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class RoleCommunity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Papel não pode estar em branco")
    private String papel;

    @OneToMany(mappedBy = "roleCommunity", cascade = CascadeType.ALL)
    private List<Membros> membros;

    public RoleCommunity() {
    }

    public RoleCommunity(String papel) {
        this.papel = papel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public List<Membros> getMembros() {
        return membros;
    }

    public void setMembros(List<Membros> membros) {
        this.membros = membros;
    }

}
