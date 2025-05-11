package com.mantenimiento.test.test_cases.docs.DocsCP_001;

import java.util.Scanner;

public class DocCP_001 {
    
    /**
     * Función para calcular el cuadrado de un número.
     * @param numero El número al que se le calculará el cuadrado.
     * @return El cuadrado del número.
     */
    public static int cuadrado(int numero) {
        return numero * numero;
    }

    /**
     * Función para calcular el factorial de un número.
     * @param numero El número al que se le calculará el factorial.
     * @return El factorial del número.
     */
    public static long factorial(int numero) {
        long resultado = 1;
        
        for (int i = 1; i <= numero; i++) {
            resultado *= i; // Multiplicación acumulativa
        }
        
        return resultado;
    }

    /**
     * Función para verificar si un número es primo.
     * @param numero El número a verificar.
     * @return true si el número es primo, false en caso contrario.
     */
    public static boolean esPrimo(int numero) {
        if (numero <= 1) {
            return false; // Los números menores o iguales a 1 no son primos
        }

        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false; // Si es divisible por otro número, no es primo
            }
        }

        return true;
    }

    /**
     * Función para calcular la potencia de un número.
     * @param base La base que se va a elevar.
     * @param exponente El exponente al que se elevará la base.
     * @return El resultado de la base elevada al exponente.
     */
    public static double potencia(double base, int exponente) {
        return Math.pow(base, exponente); // Usamos la función Math.pow() para calcular potencias
    }

    /**
     * Función para calcular la raíz cuadrada de un número.
     * @param numero El número del que se calculará la raíz cuadrada.
     * @return La raíz cuadrada del número.
     */
    public static double raizCuadrada(double numero) {
        return Math.sqrt(numero); // Usamos la función Math.sqrt() para calcular la raíz cuadrada
    }

    /**
     * Función para calcular el n-ésimo número de la secuencia de Fibonacci.
     * @param n El número de la secuencia de Fibonacci que queremos calcular.
     * @return El n-ésimo número en la secuencia de Fibonacci.
     */
    public static long fibonacci(int n) {
        if (n <= 1) {
            return n; // Casos base
        }
        
        long a = 0;
        long b = 1;
        long resultado = 0;

        // Calculamos el número de Fibonacci de manera iterativa
        for (int i = 2; i <= n; i++) {
            resultado = a + b;
            a = b;
            b = resultado;
        }

        return resultado;
    }

    /**
     * Método principal que ejecuta el programa.
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Creamos un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Pedimos al usuario que ingrese un número para operaciones básicas
        System.out.print("Ingrese un número entero: ");
        int numero = scanner.nextInt();

        // Pedimos al usuario que ingrese una base y un exponente para la potencia
        System.out.print("Ingrese la base para calcular la potencia: ");
        double base = scanner.nextDouble();
        System.out.print("Ingrese el exponente para calcular la potencia: ");
        int exponente = scanner.nextInt();

        // Calculamos y mostramos los resultados de las funciones matemáticas
        System.out.println("\nResultados de las funciones matemáticas:");

        System.out.println("El cuadrado de " + numero + " es: " + cuadrado(numero));
        System.out.println("El factorial de " + numero + " es: " + factorial(numero));
        System.out.println("El número " + numero + " es primo? " + esPrimo(numero));
        
        System.out.println("La potencia de " + base + " elevado a " + exponente + " es: " + potencia(base, exponente));
        System.out.println("La raíz cuadrada de " + numero + " es: " + raizCuadrada(numero));
        
        // Pedimos al usuario que ingrese el número para la secuencia de Fibonacci
        System.out.print("Ingrese un número para calcular el n-ésimo número de Fibonacci: ");
        int fibonacciNumero = scanner.nextInt();
        System.out.println("El " + fibonacciNumero + "-ésimo número de Fibonacci es: " + fibonacci(fibonacciNumero));

        // Cerramos el scanner
        scanner.close();
    }
    
}
