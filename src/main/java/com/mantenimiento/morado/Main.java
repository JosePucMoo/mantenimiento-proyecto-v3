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
            "C:\\Users\\josep\\OneDrive\\Escritorio\\mantenimiento-proyecto-v3\\oldVersion",
            "C:\\Users\\josep\\OneDrive\\Escritorio\\mantenimiento-proyecto-v3\\newVersion" 
        );

        try {
            Path dir = Paths.get(FileHelper.ROOT_FOLDER, FileHelper.REMOVED_FOLDER);
            FileHelper.deleteDirectoryRecursively(dir);
            dir = Paths.get(FileHelper.ROOT_FOLDER, FileHelper.ADDED_FOLDER);
            FileHelper.deleteDirectoryRecursively(dir);  
        } catch (Exception e) {
            System.err.println("Error deleting file: " + e.getMessage());
        }

        SourceFileAnalyzer analyzer = new SourceFileAnalyzer(directoryPaths);
        analyzer.analyzePath();
    }

}
