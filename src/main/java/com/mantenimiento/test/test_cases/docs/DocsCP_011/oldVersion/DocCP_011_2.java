package com.mantenimiento.test.test_cases.docs.DocsCP_011.oldVersion;

import java.util.ArrayList;
import java.util.List;

public class DocCP_011_2 {

    public static List<Integer> search(List<String> list, String term) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(term)) {
                indices.add(i);
            }
        }
        return indices;
    }
}
