package com.mantenimiento.test.test_cases;

import java.util.List;

import com.mantenimiento.morado.analyzer.SourceFileAnalyzer;

public class CP_005 {
     public static void main(String[] args) {
        List<String> testPath = List.of(
                "src/main/java/com/mantenimiento/test/test_cases/docs/DocsCP_005",
                "src/main/java/com/mantenimiento/test/test_cases/docs/DocsCP_005");
        SourceFileAnalyzer analyzer = new SourceFileAnalyzer(testPath);
        analyzer.analyzePath();
    }
}
