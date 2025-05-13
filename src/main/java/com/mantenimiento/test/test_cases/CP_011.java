package com.mantenimiento.test.test_cases;

import java.util.List;

import com.mantenimiento.morado.code.counter.SourceFileAnalyzer;

public class CP_011 {
    public static void main(String[] args) {
        List<String> directoryPaths = List.of(
            "C:\\Users\\Lenovo\\Documents\\OctavoSemestre\\Mantenimiento\\proyecto\\mantenimiento-proyecto-v3\\src\\main\\java\\com\\mantenimiento\\test\\test_cases\\docs\\DocsCP_011\\oldVersion",
            
            "C:\\Users\\Lenovo\\Documents\\OctavoSemestre\\Mantenimiento\\proyecto\\mantenimiento-proyecto-v3\\src\\main\\java\\com\\mantenimiento\\test\\test_cases\\docs\\DocsCP_011\\newVersion"
        );
        SourceFileAnalyzer analyzer = new SourceFileAnalyzer(directoryPaths);
        analyzer.analyzePath();
    }
}
