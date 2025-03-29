package com.mantenimiento.test.test_cases;

import com.mantenimiento.morado.code.counter.SourceFileAnalyzer;

public class CP_004 {
    public static void main(String[] args) {
        String testPath = "src\\main\\java\\com\\mantenimiento\\test\\test_cases\\docs\\DocCP_004.java";
        SourceFileAnalyzer analyzer = new SourceFileAnalyzer(testPath);
        analyzer.analyzePath();
    }
}
