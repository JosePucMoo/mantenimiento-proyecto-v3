package com.mantenimiento.test.test_cases.docs.DocsCP_007;

import java.util.HashMap;

public class DocCP_007 {
    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complemento = target - nums[i];
            if (map.containsKey(complemento)) {
                return new int[] { map.get(complemento), i };
            }
            map.put(nums[i], i);
        }
        return new int[] {}; 
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] resultado = twoSum(nums, target);
        System.out.println("Índices: [" + resultado[0] + ", " + resultado[1] + "]");
    }
}
