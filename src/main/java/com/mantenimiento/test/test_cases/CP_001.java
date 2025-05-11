package com.mantenimiento.test.test_cases;

import java.util.List;

import com.mantenimiento.morado.code.counter.SourceFileAnalyzer;

public class CP_001 {
    public static void main(String[] args) {
        List<String> testPath = List.of(
                "src/main/java/com/mantenimiento/test/test_cases/docs/DocsP_001",
                "src/main/java/com/mantenimiento/test/test_cases/docs/DocsCP_001");
        SourceFileAnalyzer analyzer = new SourceFileAnalyzer(testPath);
        analyzer.analyzePath();
    }
}
