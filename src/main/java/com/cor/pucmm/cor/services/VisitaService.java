package com.cor.pucmm.cor.services;

import com.cor.pucmm.cor.entidades.Visita;

public class VisitaService extends GeneralService<Visita> {
    private static VisitaService visitaServiceInstance;

    public VisitaService() {
        super(Visita.class);
    }

    public static VisitaService getInstancia() {
        if (visitaServiceInstance == null) {
            visitaServiceInstance = new VisitaService();
        }
        return visitaServiceInstance;
    }

}
