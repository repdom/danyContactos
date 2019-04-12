package com.cor.pucmm.cor.main;

import com.buck.common.codec.Codec;
import com.buck.commons.i18n.DateTime;
import com.cor.pucmm.cor.entidades.*;
import com.cor.pucmm.cor.services.UrlsService;
import com.cor.pucmm.cor.services.UsuarioService;
import com.cor.pucmm.cor.services.VisitaService;
import com.cor.pucmm.cor.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import freemarker.template.Configuration;
import org.h2.util.DateTimeUtils;
import org.jasypt.util.text.StrongTextEncryptor;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Spark;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.sql.Array;
import java.util.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {
    public final static String ACCEPT_TYPE_JSON = "application/json";
    public final static String ACCEPT_TYPE_XML = "application/xml";
    public final static int BAD_REQUEST = 400;
    public final static int ERROR_INTERNO = 500;
    private static String contrasenia = "proyectoFinal";


    public static void main(String[] args) {
        DbService.getInstancia().iniciarDn();
        Configuration configuration = new Configuration(Configuration.getVersion());
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        crearEntidades();
        enableDebugScreen();
        Codec codec = Codec.forName("Base16");
        /*byte[] toEncoded = codec.newEncoder().encode(1);
        for (byte b: toEncoded) {
            System.out.println(b);
        }*/
        /*Shortener u = new Shortener(5, "www.tinyurl.com/");
        String urls[] = { "www.google.com/", "www.google.com",
                "http://www.yahoo.com", "www.yahoo.com/", "www.amazon.com",
                "www.amazon.com/page1.php", "www.amazon.com/page2.php",
                "www.flipkart.in", "www.rediff.com", "www.techmeme.com",
                "www.techcrunch.com", "www.lifehacker.com", "www.icicibank.com" };

        for (int i = 0; i < urls.length; i++) {
            System.out.println("URL:" + urls[i] + "\tTiny: "
                    + u.shortenURL(urls[i]) + "\tExpanded: "
                    + u.expandURL(u.shortenURL(urls[i])));
        }*/
        exception(IllegalArgumentException.class, (exception, request, response) -> {
            manejarError(Main.BAD_REQUEST, exception, request, response);
        });

        exception(JsonSyntaxException.class, (exception, request, response) -> {
            manejarError(Main.BAD_REQUEST, exception, request, response);
        });

        exception(Exception.class, (exception, request, response) -> {
            manejarError(Main.ERROR_INTERNO, exception, request, response);
        });
        afterAfter("/*", (request, response) -> {
            response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
            response.header("Access-Control-Allow-Origin", "*");
            response.status(200);
        });
        path("/Api", () -> {
                Spark.get("/", (request, response) -> {
                    return "Empezando Acrotador URL";
                }, JsonUtils.json());
                Spark.post("/agregandoUrl", Main.ACCEPT_TYPE_JSON, (request, response) -> {
                    System.out.println(request.body());
                    UrlS url = new Gson().fromJson(request.body(), UrlS.class);
                    Shortener u = new Shortener(5, "www.tinyurl.com/");
                    url.setHashMaked(u.getKey(url.getUrl()));
                    UrlsService.getInstancia().crear(url);
                    // Shortener u = new Shortener(5, "www..com/");
                    return url;
                }, JsonUtils.json());
                Spark.post("/agregandoUrl/:usuario", Main.ACCEPT_TYPE_JSON, (request, response) -> {
                    Usuario usuario = UsuarioService.getInstancia().find(request.params("usuario"));

                    UrlS url = new Gson().fromJson(request.body(), UrlS.class);
                    url.setUsuario(usuario);
                    Shortener u = new Shortener(5, "www.tinyurl.com/");
                    url.setHashMaked(u.getKey(url.getUrl()));
                    UrlsService.getInstancia().crear(url);
                    url.setVisitas(null);
                    url.setUsuario(null);

                    return url;
                }, JsonUtils.json());
                Spark.get("/redireccionar/:hash", (request, response) -> {
                    String p = request.params("hash");
                    System.out.println(p);
                    UrlS urls = UrlsService.getInstancia().findByHash(p);
                    // System.out.println(urls.getUrl());
                    Visita visita = new Visita();
                    // visita.setFechaGuardado(Date.valueOf(LocalDate.now()));
                    visita.setIp(request.ip());
                    visita.setNavegador_web(request.userAgent());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    visita.setHora(new Time(date.getTime()));
                    visita.setFechaGuardado(date);
                    visita.setUrlS(urls);
                    urls.setVisitas(null);
                    // visita.setSistema_operativo(request.);
                    VisitaService.getInstancia().crear(visita);
                    urls.setUsuario(null);
                    return urls;
                }, JsonUtils.json());
                Spark.get("/estadistica/:hash", (request, response) -> {
                    String p = request.params("hash");
                    ArrayList<Visita> visitas = new ArrayList<>();
                    // System.out.println(p);
                    UrlS urls = UrlsService.getInstancia().findByHash(p);
                    // System.out.println(urls.getVisitas().size());
                    for (Visita v : urls.getVisitas()) {
                        v.setUrlS(null);
                        visitas.add(v);
                    }
                    // Map<String, Object> attributes = new HashMap<>();
                    //attributes.put("visitas", attributes);
                    return visitas;
                }, JsonUtils.json());
            path("/usuarios", () -> {
                    post("/", Main.ACCEPT_TYPE_JSON, (request, response) -> {
                        Usuario usuario = new Gson().fromJson(request.body(), Usuario.class);

                        UsuarioService.getInstancia().crear(usuario);

                        return usuario;
                    }, JsonUtils.json());
                    get("/", (request, response) -> {
                        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
                        List<Usuario> usuarios = UsuarioService.getInstancia().findAll();
                        for (Usuario u: UsuarioService.getInstancia().findAll()) {
                            u.setListaUrls(null);
                            listaUsuarios.add(u);
                        }
                        return listaUsuarios;
                    }, JsonUtils.json());
                    get("/login/:usuario/:contrasenia", (request, response) -> {
                        Usuario usuario = UsuarioService.getInstancia().find(request.params("usuario"));
                        if (usuario.getContrasenia().equals(request.params("contrasenia")) && usuario.getUsuario().equals(request.params("usuario"))) {
                            // StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
                            // textEncryptor.setPassword(contrasenia);
                            // String userEncripted = textEncryptor.encrypt(usuario.getUsuario());
                            //String userAdmin = textEncryptor.encrypt(String.valueOf(usuario.isAdministrador()));
                            // String userRol = textEncryptor.encrypt(String.valueOf(usuario.getRol()));
                            // int esMantenerSesion = 1000;
                            // response.cookie("/", "usuario", userEncripted, esMantenerSesion, false);
                            // response.cookie("/", "userRol", userRol, esMantenerSesion, false);
                            // response.cookie("/", "esAutor", userAutor, esMantenerSesion, false);
                            return usuario;
                        } else {
                            return null;
                        }
                    }, JsonUtils.json());
                    get("/urls/:usuario", (request, response) -> {
                        ArrayList<UrlS> arrayListUrls = new ArrayList<>();
                        Usuario usuario = UsuarioService.getInstancia().find(request.params("usuario"));
                        if (usuario.getListaUrls().size() <= 0) {
                            return null;
                        }
                        for (UrlS u: usuario.getListaUrls()) {
                            u.setUsuario(null);
                            u.setVisitas(null);
                            arrayListUrls.add(u);
                        }
                        return arrayListUrls;
                    }, JsonUtils.json());
            });
            path("/url", () -> {
                get("/urls", (request, response) -> {
                    return UrlsService.getInstancia().findAll();
                }, JsonUtils.json());
            });
        });
    }
    private static Response guardarUsuarioEncriptado(Usuario usuario, Response response, Request request) {
        return response;
    }
    private static boolean estaLogueado(Request request) {
        StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
        textEncryptor.setPassword(contrasenia);

        String usuario = textEncryptor.decrypt(request.cookie("usuario"));
        String password = textEncryptor.decrypt(request.cookie("contrasenia"));

        if(usuario == null && password == null) {
            return false;
        }
        return true;
    }
    private static void crearEntidades() {
        EntityManagerFactory emf =  Persistence.createEntityManagerFactory("MiUnidadPersistencia");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Usuario usuario = new Usuario();
        usuario.setApellido("manager");
        usuario.setApellidoSegundo("manager");
        usuario.setContrasenia("manager123");
        usuario.setEmail("juan.thomas.angel@gmail.com");
        usuario.setNombre("manager");
        usuario.setNombreSegundo("manager");
        usuario.setRol("manager");
        usuario.setUsuario("manager");
        UsuarioService.getInstancia().crear(usuario);
        //entityManager.persist(usuario);
        // entityManager.getTransaction().commit();
    }

    // maneja error HTTP
    private static void manejarError(int codigo, Exception exception, Request request, Response response) {
        response.status(codigo);
        response.body(JsonUtils.toJson(new ErrorResponse(100, exception.getMessage())));
        exception.printStackTrace();
    }

}
