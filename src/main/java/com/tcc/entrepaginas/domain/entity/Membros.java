package com.tcc.entrepaginas.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Membros implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "roleCommunity_id")
    private RoleCommunity roleCommunity;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

    public Membros() {}

    public Membros(Usuario usuario, RoleCommunity roleCommunity, Community community) {
        this.usuario = usuario;
        this.roleCommunity = roleCommunity;
        this.community = community;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public RoleCommunity getRoleCommunity() {
        return roleCommunity;
    }

    public void setRoleCommunity(RoleCommunity roleCommunity) {
        this.roleCommunity = roleCommunity;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }
}
