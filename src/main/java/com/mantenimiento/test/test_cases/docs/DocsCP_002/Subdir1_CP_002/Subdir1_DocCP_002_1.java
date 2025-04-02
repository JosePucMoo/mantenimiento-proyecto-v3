package com.mantenimiento.test.test_cases.docs.DocsCP_002.Subdir1_CP_002;

import java.util.Scanner;

public class Subdir1_DocCP_002_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Ingrese la cantidad de términos: ");
        int n = scanner.nextInt();
        
        generarFibonacci(n);

        scanner.close();
    }

    public static void generarFibonacci(int n) {
        int a = 0;
        int b = 1;
        int temp;
        
        for (int i = 0; i < n; i++) {
            System.out.print(a + " ");
            temp = a + b;
            a = b;
            b = temp;
        }
        System.out.println();
    }
}
