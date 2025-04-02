package com.mantenimiento.test.test_cases.docs.DocsCP_005;

public class DocCP_005 {
    public static String convertirMayusculas(String texto) {
        return texto.toUpperCase();
    }

    public static String convertirMinusculas(String texto) {
        return texto.toLowerCase();
    }

    public static int contarVocales(String texto) {
        int contador = 0;
        String vocales = "aeiouAEIOU";
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (vocales.indexOf(c) != -1) {
                contador++;
            }
        }
        return contador;
    }

    public static boolean contienePalabra(String texto, String palabra) {
        return texto.contains(palabra);
    }

    public static String reemplazarPalabra(String texto, String buscar, String reemplazo) {
        return texto.replace(buscar, reemplazo);
    }
}
