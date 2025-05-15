package com.mantenimiento.test.test_cases;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.mantenimiento.morado.code.counter.SourceFileAnalyzer;
import com.mantenimiento.morado.util.FileHelper;

public class CP_013 {
    public static void main(String[] args) {
        List<String> directoryPaths = List.of(
            "src\\main\\java\\com\\mantenimiento\\test\\test_cases\\docs\\DocsCP_013\\oldVersion",
            "src\\main\\java\\com\\mantenimiento\\test\\test_cases\\docs\\DocsCP_013\\newVersion" 
        );

        try {
            Path dir = Paths.get(FileHelper.ROOT_COMPARISON_FOLDER, FileHelper.REMOVED_FOLDER);
            FileHelper.deleteDirectoryRecursively(dir);
            dir = Paths.get(FileHelper.ROOT_COMPARISON_FOLDER, FileHelper.ADDED_FOLDER);
            FileHelper.deleteDirectoryRecursively(dir);  
        } catch (Exception e) {
            System.err.println("Error deleting file: " + e.getMessage());
        }

        SourceFileAnalyzer analyzer = new SourceFileAnalyzer(directoryPaths);
        analyzer.analyzePath();
    }    
}
