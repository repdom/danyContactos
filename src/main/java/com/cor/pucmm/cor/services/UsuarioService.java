package com.cor.pucmm.cor.services;


import com.cor.pucmm.cor.entidades.Usuario;

public class UsuarioService extends GeneralService<Usuario> {
    private static UsuarioService usuarioerviceInstance;

    public UsuarioService() {
        super(Usuario.class);
    }

    public static UsuarioService getInstancia() {
        if (usuarioerviceInstance == null) {
            usuarioerviceInstance = new UsuarioService();
        }
        return usuarioerviceInstance;
    }
}