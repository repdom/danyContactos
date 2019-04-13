package com.cor.pucmm.cor.soap;

import com.cor.pucmm.cor.entidades.UrlS;
import com.cor.pucmm.cor.entidades.Usuario;
import com.cor.pucmm.cor.services.UsuarioService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@WebService
public class UsuarioWebService {

    @WebMethod
    public List<UrlDao> getUrl(String usuario) {
        List<UrlDao> urlDaos = new ArrayList<>();
        Usuario user = UsuarioService.getInstancia().find(usuario);
        Set<UrlS> urls = user.getListaUrls();
        // ArrayList<UrlS> urlsList = new ArrayList<>();
        for (UrlS url: urls) {
            UrlDao urlDao = new UrlDao();
            urlDao.setHashMaked(url.getHashMaked());
            urlDao.setUrl(url.getUrl());
            urlDaos.add(urlDao);
        }
        return urlDaos;
    }
}
