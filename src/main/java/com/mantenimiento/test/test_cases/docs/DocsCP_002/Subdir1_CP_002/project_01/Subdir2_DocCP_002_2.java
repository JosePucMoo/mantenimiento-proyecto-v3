package com.mantenimiento.test.test_cases.docs.DocsCP_002.Subdir1_CP_002.project_01;

import java.util.Scanner;

public class Subdir2_DocCP_002_2 {
    public static void main(String[] args) {
        String usuarioCorrecto = "admin";
        String contrasenaCorrecta = "1234";

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su usuario: ");
        String usuarioIngresado = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contrasenaIngresada = scanner.nextLine();

        if (usuarioIngresado.equals(usuarioCorrecto) && contrasenaIngresada.equals(contrasenaCorrecta)) {
            System.out.println("Acceso concedido. Bienvenido, " + usuarioIngresado + "!");
        } else if (!usuarioIngresado.equals(usuarioCorrecto)) {
            System.out.println("Error: Usuario incorrecto.");
        } else {
            System.out.println("Error: Contraseña incorrecta.");
        }

        scanner.close();
    }
}
