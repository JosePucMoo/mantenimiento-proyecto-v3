package com.mantenimiento.morado;

import java.util.List;

import com.mantenimiento.morado.code.counter.SourceFileAnalyzer;

public class Main {
    public static void main(String[] args) {
        /* if (args.length == 0 || args[0].isEmpty()) {
            System.out.println("Review the User Manual");
            return;
        } */
        
        List<String> directoryPaths = List.of(
            "C:/Users/Lenovo/Documents/OctavoSemestre/Mantenimiento/proyecto/mantenimiento-proyecto-v3/oldVersion",
            "C:/Users/Lenovo/Documents/OctavoSemestre/Mantenimiento/proyecto/mantenimiento-proyecto-v3/newVersion"
        );
        SourceFileAnalyzer analyzer = new SourceFileAnalyzer(directoryPaths);
        analyzer.analyzePath();
    }

}
