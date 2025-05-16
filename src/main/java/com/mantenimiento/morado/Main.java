package com.mantenimiento.morado;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.mantenimiento.morado.analyzer.SourceFileAnalyzer;
import com.mantenimiento.morado.util.FileHelper;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || args[0].isEmpty() || args[1].isEmpty()) {
            System.out.println("Review the User Manual");
            return;
        }
        
        List<String> directoryPaths = List.of(
            args[0],
            args[1]
        );

        try {
            Path dir = Paths.get(FileHelper.ROOT_COMPARISON_FOLDER, FileHelper.REMOVED_FOLDER);
            FileHelper.deleteDirectoryRecursively(dir);
            dir = Paths.get(FileHelper.ROOT_COMPARISON_FOLDER, FileHelper.ADDED_FOLDER);
            FileHelper.deleteDirectoryRecursively(dir);  

            dir = Paths.get(FileHelper.ROOT_FORMATTED_FOLDER);
            FileHelper.deleteDirectoryRecursively(dir);
        } catch (Exception e) {
            System.err.println("Error deleting file: " + e.getMessage());
        }

        try {
            SourceFileAnalyzer analyzer = new SourceFileAnalyzer(directoryPaths);
            analyzer.analyzePath();
        } catch (Exception e) {
            System.err.println("Error deleting file: " + e.getMessage());
        }
    }
}
