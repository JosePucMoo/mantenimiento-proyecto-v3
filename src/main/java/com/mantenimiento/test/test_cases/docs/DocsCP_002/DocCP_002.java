package com.mantenimiento.test.test_cases.docs.DocsCP_002;

import java.util.Scanner;

public class DocCP_002 {
    // Este es el método principal que ejecuta el programa
    public static void main(String[] args) {
        
        // Creamos una instancia de Scanner para capturar datos de la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Imprimimos un mensaje para pedir al usuario que ingrese el primer número
        System.out.print("Ingrese el primer número: ");
        
        // Leemos el primer número ingresado por el usuario
        int numero1 = scanner.nextInt();  // Utilizamos nextInt() para leer un número entero

        // Imprimimos un mensaje para pedir al usuario que ingrese el segundo número
        System.out.print("Ingrese el segundo número: ");
        
        // Leemos el segundo número ingresado por el usuario
        int numero2 = scanner.nextInt();  // Utilizamos nextInt() para leer un número entero

        // Realizamos la suma de los dos números
        int resultado = numero1 + numero2;  // La suma se almacena en la variable "resultado"

        // Mostramos el resultado de la suma
        System.out.println("El resultado de la suma es: " + resultado);  // Imprimimos el resultado

        // Cerramos el scanner para liberar recursos
        scanner.close();
    }
}
