package com.mantenimiento.test.test_cases.docs.DocsCP_005.Subdir1_CP_005;

public class Subdir1_DocCP_005_1 {
    public static int sumar(int a, int b) {
        return a + b;
    }

    public static int restar(int a, int b) {
        return a - b;
    }

    public static int multiplicar(int a, int b) {
        return a * b;
    }

    public static double dividir(int a, int b) {
        if (b == 0) {
            System.out.println("Error: División por cero.");
            return Double.NaN;
        }
        return (double) a / b;
    }

    public static int encontrarMaximo(int[] numeros) {
        int max = numeros[0];
        for (int i = 1; i < numeros.length; i++) {
            if (numeros[i] > max) {
                max = numeros[i];
            }
        }
        return max;
    }
}
