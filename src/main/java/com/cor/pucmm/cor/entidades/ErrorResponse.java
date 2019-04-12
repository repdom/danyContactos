package com.cor.pucmm.cor.entidades;

public class ErrorResponse {
    int codigo;
    String mensaje;

    public ErrorResponse(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
