package com.tcc.entrepaginas.domain;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Papel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Nome do papel deve ser válido")
    private String papelNome;

    @OneToMany(mappedBy = "papel")
    private List<Usuario> usuarios;

    public Papel() {
    }

    public Papel(String papelNome) {
        this.papelNome = papelNome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPapelNome() {
        return papelNome;
    }

    public void setPapelNome(String papelNome) {
        this.papelNome = papelNome;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
