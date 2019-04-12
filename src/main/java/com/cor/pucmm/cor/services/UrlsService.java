package com.cor.pucmm.cor.services;

import com.cor.pucmm.cor.entidades.UrlS;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UrlsService extends GeneralService<UrlS> {
    private static UrlsService urlsServiceInstance;

    public UrlsService() {
        super(UrlS.class);
    }

    public static UrlsService getInstancia() {
        if (urlsServiceInstance == null) {
            urlsServiceInstance = new UrlsService();
        }
        return urlsServiceInstance;
    }

    public UrlS findByHash(String hash){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("from UrlS where hashMaked = :hash");
        query.setParameter("hash", hash);
        List<UrlS> lista = query.getResultList();
        // System.out.println(lista.get(0).getUrl());
        return lista.get(0);
    }
}
