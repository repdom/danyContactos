package com.cor.pucmm.cor.entidades;

import org.h2.util.DateTimeUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Visita implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long codigo;

    public String ip;

    public String navegador_web;

    public String sistema_operativo;

    public Time hora;

    public Date fechaGuardado;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    public UrlS urlS;

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public void setFechaGuardado(Date fechaGuardado) {
        this.fechaGuardado = fechaGuardado;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNavegador_web() {
        return navegador_web;
    }

    public void setNavegador_web(String navegador_web) {
        this.navegador_web = navegador_web;
    }

    public String getSistema_operativo() {
        return sistema_operativo;
    }

    public void setSistema_operativo(String sistema_operativo) {
        this.sistema_operativo = sistema_operativo;
    }


    public UrlS getUrlS() {
        return urlS;
    }

    public void setUrlS(UrlS urlS) {
        this.urlS = urlS;
    }
}
