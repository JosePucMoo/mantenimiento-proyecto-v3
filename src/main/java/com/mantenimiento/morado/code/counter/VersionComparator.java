package com.mantenimiento.morado.code.counter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mantenimiento.morado.code.model.JavaProject;
import com.mantenimiento.morado.code.model.SourceFile;

public class VersionComparator {
    public static void compareVersions(List<JavaProject> projectsToCompare){
        JavaProject oldVersion = projectsToCompare.get(0);
        JavaProject newVersion = projectsToCompare.get(1);

        Map<String, SourceFile> oldVersionFiles = new HashMap<>();
        for (SourceFile sourceFile : oldVersion.getSourceFiles()) {
            oldVersionFiles.put(sourceFile.filename(), sourceFile);
        }

        for (SourceFile newFile : newVersion.getSourceFiles()) {
            String newfilename = newFile.filename();
            if (oldVersionFiles.containsKey(newfilename)) {
                SourceFile oldFile = oldVersionFiles.get(newfilename);
                try {
                    List<String> oldCodeLinesFile = SourceFile.getAllLinesFromFile(oldFile.filePath());
                    List<String> newCodeLinesFile = SourceFile.getAllLinesFromFile(newFile.filePath());
                    int numDeletedLines = DeletedLinesCounter.count(
                        oldCodeLinesFile, 
                        newCodeLinesFile,
                        oldFile.filename()
                    );
                    oldFile = DeletedLinesCounter.createSourceFilewithDeletedLines(oldFile.filePath(), numDeletedLines);
                } catch (IOException ioException) {
                    System.out.println("Error while reading file: " + ioException.getMessage());
                }
            }
        }
    }
}
