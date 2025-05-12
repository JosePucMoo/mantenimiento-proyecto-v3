package com.mantenimiento.morado;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.mantenimiento.morado.code.counter.FileFormatter;
import com.mantenimiento.morado.code.counter.SourceFileAnalyzer;
import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.util.FileHelper;

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

        try {
            String newVersionPath = directoryPaths.get(1);
            File folder = new File(newVersionPath);
            File[] files = folder.listFiles((dir, name) -> name.endsWith(".java"));

            if (files != null) {
                FileFormatter formatter = new FileFormatter();

                for (File file : files) {
                    String filename = file.getName();
                    String filePath = file.getAbsolutePath();

                    // Intenta encontrar archivo correspondiente en oldVersion
                    String oldVersionPath = directoryPaths.get(0) + "/" + filename;
                    File oldFileCandidate = new File(oldVersionPath);
                    SourceFile oldFile = oldFileCandidate.exists() ? new SourceFile(filename, oldVersionPath) : null;

                    SourceFile newFile = new SourceFile(filename, filePath);
                    formatter.formatFile(oldFile, newFile);
                    System.out.println("Formateado: " + filename);
                }
            } else {
                System.out.println("No se encontraron archivos .java en la carpeta newVersion.");
            }
        } catch (Exception e) {
            System.err.println("Error al formatear archivos: " + e.getMessage());
        }
        
    }

}
