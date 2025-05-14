package com.mantenimiento.test.test_cases.docs.DocsCP_002.Subdir2_CP_002.project_02;

import java.util.Scanner;

public class Subdir2_DocCP_002_1 {
    private static double saldo = 1000; // Saldo inicial

    public static void depositar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad;
            System.out.println("Depósito exitoso. Nuevo saldo: $" + saldo);
        } else {
            System.out.println("Error: La cantidad a depositar debe ser positiva.");
        }
    }

    public static void retirar(double cantidad) {
        if (cantidad > 0 && cantidad <= saldo) {
            saldo -= cantidad;
            System.out.println("Retiro exitoso. Nuevo saldo: $" + saldo);
        } else {
            System.out.println("Error: Saldo insuficiente o cantidad no válida.");
        }
    }

    public static void mostrarSaldo() {
        System.out.println("Saldo actual: $" + saldo);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú Banco ---");
            System.out.println("1. Depositar");
            System.out.println("2. Retirar");
            System.out.println("3. Mostrar saldo");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese cantidad a depositar: ");
                    double deposito = scanner.nextDouble();
                    depositar(deposito);
                    break;
                case 2:
                    System.out.print("Ingrese cantidad a retirar: ");
                    double retiro = scanner.nextDouble();
                    retirar(retiro);
                    break;
                case 3:
                    mostrarSaldo();
                    break;
                case 4:
                    System.out.println("Gracias por usar nuestro banco.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 4);

        scanner.close();
    }
}
