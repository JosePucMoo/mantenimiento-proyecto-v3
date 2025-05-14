package com.mantenimiento.test.test_cases.docs.DocsCP_004;

import java.util.Arrays;

public class DocCP_004 {
    public static int sumar(int a, int b) {
        return a + b;
    }

    public static String invertirCadena(String texto) {
        StringBuilder sb = new StringBuilder(texto);
        return sb.reverse().toString();
    }

    public static boolean esPrimo(int numero) {
        if (numero < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int encontrarMaximo(int[] numeros) {
        return Arrays.stream(numeros).max().orElse(Integer.MIN_VALUE);
    }

    public static boolean esPalindromo(String texto) {
        String invertida = invertirCadena(texto);
        return texto.equalsIgnoreCase(invertida);
    }

    public static long factorial(int n) {
        if (n < 0) {
            return -1;
        }
        long resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }
}
