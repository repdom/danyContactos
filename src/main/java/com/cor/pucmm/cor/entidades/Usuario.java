package com.cor.pucmm.cor.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Usuario implements Serializable {
    @Id
    private String usuario;
    private String nombre;
    private String nombreSegundo;
    private String apellido;
    private String apellidoSegundo;
    private String email;

    @Column(columnDefinition = "LONGTEXT")
    private String contrasenia;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private Set<UrlS> listaUrls;

    private String rol;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Set<UrlS> getListaUrls() {
        return listaUrls;
    }

    public void setListaUrls(Set<UrlS> listaUrls) {
        this.listaUrls = listaUrls;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreSegundo() {
        return nombreSegundo;
    }

    public void setNombreSegundo(String nombreSegundo) {
        this.nombreSegundo = nombreSegundo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellidoSegundo() {
        return apellidoSegundo;
    }

    public void setApellidoSegundo(String apellidoSegundo) {
        this.apellidoSegundo = apellidoSegundo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
