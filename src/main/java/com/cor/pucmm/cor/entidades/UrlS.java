package com.cor.pucmm.cor.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class UrlS implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long codigo;
    @Column(columnDefinition = "LONGTEXT")
    private String url;
    @Column(columnDefinition = "LONGTEXT")
    private String hashMaked;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @OneToMany(mappedBy = "urlS", fetch = FetchType.EAGER)
    private Set<Visita> visitas;

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHashMaked() {
        return hashMaked;
    }

    public void setHashMaked(String hashMaked) {
        this.hashMaked = hashMaked;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Visita> getVisitas() {
        return visitas;
    }

    public void setVisitas(Set<Visita> visitas) {
        this.visitas = visitas;
    }

}
