package com.mantenimiento.test.test_cases.docs.DocsCP_015;

public class DocCP_015 {

    // Método que recibe múltiples parámetros y los concatena en una sola línea con formato.
    public static String construirMensaje(String nombre, int edad, String ciudad, String profesion,boolean activo, double salario) {
        return "Nombre: " + nombre + ", Edad: " + edad + ", Ciudad: " + ciudad + ", Profesión: " + profesion + ", Activo: " + activo + ", Salario: $" + salario;
    }

    // Método que verifica si una cadena es palíndroma ignorando mayúsculas y espacios.
    public static boolean esPalindromo(String texto) {
        String invertido = new StringBuilder(texto.replaceAll("\\s+", "").toLowerCase()).reverse().toString();
        return texto.replaceAll("\\s+", "").toLowerCase().equals(invertido);
    }
}
