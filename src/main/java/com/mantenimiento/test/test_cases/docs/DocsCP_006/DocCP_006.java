package com.mantenimiento.test.test_cases.docs.DocsCP_006;

import java.util.Scanner;

public class DocCP_006 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numero1 = scanner.nextInt();
        int numero2 = scanner.nextInt();
        System.out.println(numero1 + numero2);
        scanner.close();
    }
}
