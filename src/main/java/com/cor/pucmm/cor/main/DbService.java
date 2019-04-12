package com.cor.pucmm.cor.main;

import org.h2.tools.Server;

import java.sql.SQLException;

public class DbService {
    private static DbService instancia;

    public static DbService getInstancia() {
        if (instancia == null) {
            instancia = new DbService();
        }
        return instancia;
    }

    public void iniciarDn() {
        try {
            Server.createTcpServer("-tcpPort","9092","-tcpAllowOthers","-tcpDaemon").start();

        } catch (SQLException ex) {
            System.out.println("Problema con la base de datos: "+ex.getMessage());
        }
    }
    public void init(){
        iniciarDn();
    }

}
