package com.mantenimiento.morado;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.mantenimiento.morado.code.counter.SourceFileAnalyzer;
import com.mantenimiento.morado.util.FileHelper;

public class Main {
    public static void main(String[] args) {
        /* if (args.length == 0 || args[0].isEmpty()) {
            System.out.println("Review the User Manual");
            return;
        } */
        
        List<String> directoryPaths = List.of(
            "C:\\Users\\josep\\OneDrive\\Escritorio\\mantenimiento-proyecto-v3\\src\\main\\java\\com\\mantenimiento\\morado"
        );
        SourceFileAnalyzer analyzer = new SourceFileAnalyzer(directoryPaths);
        analyzer.analyzePath();
    }

}
