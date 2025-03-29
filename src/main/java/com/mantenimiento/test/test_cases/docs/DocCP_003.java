package com.mantenimiento.test.test_cases.docs;

/**
 * Clase que representa una calculadora simple.
 * Proporciona métodos para realizar operaciones básicas como la suma y resta.
 */
public class DocCP_003 {
 
    /**
     * Suma dos números enteros.
     * @param a El primer número.
     * @param b El segundo número.
     * @return El resultado de sumar a y b.
     */
    public int sumar(int a, int b) {
        return a + b; // Realiza la suma y retorna el resultado
    }

    /**
     * Resta dos números enteros.
     * @param a El primer número.
     * @param b El segundo número.
     * @return El resultado de restar b a a.
     */
    public int restar(int a, int b) {
        return a - b; // Realiza la resta y retorna el resultado
    
    }
    public static void main(String[] args) {
        // Creamos una instancia de la calculadora
        DocCP_003 calc = new DocCP_003();

        // Realizamos algunas operaciones
        int suma = calc.sumar(5, 3); // 5 + 3
        int resta = calc.restar(5, 3); // 5 - 3

        // Imprimimos los resultados
        System.out.println("Suma: " + suma);
        System.out.println("Resta: " + resta);
        
        /*
         * El siguiente bloque de código es para verificar
         * que las operaciones se hayan realizado correctamente.
         * Si alguna operación da un valor inesperado, se
         * debería revisar el código.
         */
        if (suma == 8 && resta == 2) {
            System.out.println("Las operaciones fueron correctas.");
        } else {
            System.out.println("Hubo un error en las operaciones.");
        }
    }
}


