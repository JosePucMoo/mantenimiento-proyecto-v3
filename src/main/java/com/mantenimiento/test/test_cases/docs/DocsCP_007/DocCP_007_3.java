package com.mantenimiento.test.test_cases.docs.DocsCP_007;

public class DocCP_007_3 {
    public static int firstMissingPositive(int[] nums) {
        int n = nums.length;

        // Asegurarnos de que solo consideramos números dentro del rango [1, n]
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                // Intercambiamos los elementos para asegurarnos de que cada número
                // está en su posición correcta si es posible.
                int temp = nums[nums[i] - 1];
                nums[nums[i] - 1] = nums[i];
                nums[i] = temp;
            }
        }

        // Después de la reorganización, el primer número que no esté en su
        // lugar corresponde al primer número positivo faltante.
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        // Si todos los números están en su lugar, entonces el número faltante es n + 1
        return n + 1;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 0};
        System.out.println("Primer número positivo faltante: " + firstMissingPositive(nums1));  // Salida: 3

        int[] nums2 = {3, 4, -1, 1};
        System.out.println("Primer número positivo faltante: " + firstMissingPositive(nums2));  // Salida: 2
    }
}
