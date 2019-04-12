package com.cor.pucmm.cor.entidades;

import java.util.HashMap;
import java.util.Random;

public class Shortener {
    // sona de guardado de claves generadas
    private HashMap<String, String> keyMap; // llave de url map
    private HashMap<String, String> valueMap;// llave de url para eencontrarlo rápido

    private String domain; // Utilice este atributo para generar urls custms

    // nombre de dominio por defecto to http://fkt.in
    private char myChars[]; // Esta matriz se utiliza para carácter a número
    // mapping
    private Random myRand; // Objeto aleatorio usado para generar enteros aleatorios.
    private int keyLength; // la longitud de la clave en URL por defecto es 8

    // Constructor por defecto
    public Shortener() {
        keyMap = new HashMap<String, String>();
        valueMap = new HashMap<String, String>();
        myRand = new Random();
        keyLength = 8;
        myChars = new char[62];
        for (int i = 0; i < 62; i++) {
            int j = 0;
            if (i < 10) {
                j = i + 48;
            } else if (i > 9 && i <= 35) {
                j = i + 55;
            } else {
                j = i + 61;
            }
            myChars[i] = (char) j;
        }
        domain = "http://fkt.in";
    }

    // Constructor que le permite definir la longitud de la clave de la URL pequeña y la URL base
    public Shortener(int length, String newDomain) {
        this();
        this.keyLength = length;
        if (!newDomain.isEmpty()) {
            newDomain = sanitizeURL(newDomain);
            domain = newDomain;
        }
    }

    // shortenURL
    // El método público que se puede llamar para acortar una URL determinada
    public String shortenURL(String longURL) {
        String shortURL = "";
        if (validateURL(longURL)) {
            longURL = sanitizeURL(longURL);
            if (valueMap.containsKey(longURL)) {
                shortURL = domain + "/" + valueMap.get(longURL);
            } else {
                shortURL = domain + "/" + getKey(longURL);
            }
        }
        return shortURL;
    }

    // expandURL
    // Método público que devuelve la URL original dada la url reducida.
    public String expandURL(String shortURL) {
        String longURL = "";
        String key = shortURL.substring(domain.length() + 1);
        longURL = keyMap.get(key);
        return longURL;
    }

    // Validar URL
    // no implementado, pero debe implementarse para verificar si la URL dada
    // es valido o no
    boolean validateURL(String url) {
        return true;
    }

    // sanitizeURL
    // Este método resuelve varios problemas con una url válida
    // e.g. www.google.com,www.google.com/, http://www.google.com,
    // http://www.google.com/
    // Todas las URL anteriores deben apuntar a la misma URL abreviada
    // Podría haber varios otros casos como estos.
    String sanitizeURL(String url) {
        if (url.substring(0, 7).equals("http://"))
            url = url.substring(7);

        if (url.substring(0, 8).equals("https://"))
            url = url.substring(8);

        if (url.charAt(url.length() - 1) == '/')
            url = url.substring(0, url.length() - 1);
        return url;
    }

    /*
     * get Key
     */
    public String getKey(String longURL) {
        String key;
        key = generateKey();
        keyMap.put(key, longURL);
        valueMap.put(longURL, key);
        return key;
    }

    // generar Key
    private String generateKey() {
        String key = "";
        boolean flag = true;
        while (flag) {
            key = "";
            for (int i = 0; i <= keyLength; i++) {
                key += myChars[myRand.nextInt(62)];
            }
            // System.out.println("Iteration: "+ counter + "Key: "+ key);
            if (!keyMap.containsKey(key)) {
                flag = false;
            }
        }
        return key;
    }
}
