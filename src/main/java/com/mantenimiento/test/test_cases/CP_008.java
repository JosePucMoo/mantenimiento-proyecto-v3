package com.mantenimiento.test.test_cases;

import java.util.List;

import com.mantenimiento.morado.analyzer.SourceFileAnalyzer;

public class CP_008 {
    public static void main(String[] args) {
        List<String> testPath = List.of(
                "src/main/java/com/mantenimiento/test/test_cases/docs/DocsCP_008",
                "src/main/java/com/mantenimiento/test/test_cases/docs/DocsCP_008");
        SourceFileAnalyzer analyzer = new SourceFileAnalyzer(testPath);
        analyzer.analyzePath();
    }
}
