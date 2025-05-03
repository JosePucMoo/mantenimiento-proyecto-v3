package com.mantenimiento.morado.code.counter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.util.FileHelper;

public class DeletedLinesCounter {
    private static final String DELETED_LINE_TAG = " // línea borrada";
    
    public static int count(
        List<String> oldCodeLinesVersion, 
        List<String> newCodeLinesVersion,
        String fileName
    ) {
        List<String> oldCodeLinesFile = cleanLines(oldCodeLinesVersion);
        List<String> newCodeLinesFile = cleanLines(newCodeLinesVersion);
        int deletedLinesCounter = 0;
        int minSize = Math.min(oldCodeLinesFile.size(), newCodeLinesFile.size());

        for (int i = 0; i < minSize; i++) {
            String oldLine = oldCodeLinesFile.get(i);
            String newLine = newCodeLinesFile.get(i);

            if(!oldLine.equals(newLine)){
                markAsDeleted(oldCodeLinesFile, i, oldLine);
                deletedLinesCounter++;
            }
        }

        // Remaining lines in old version: removed
        for (int i = minSize; i < oldCodeLinesFile.size(); i++) {
            String oldLine = oldCodeLinesFile.get(i);
            markAsDeleted(oldCodeLinesFile, i, oldLine);
            deletedLinesCounter++;
        }

        FileHelper.writeLinesByTag(oldCodeLinesFile, fileName, "deleted");
        
        return deletedLinesCounter;
    }

    private static void markAsDeleted(List<String> codeLines, int index, String line) {
        String markedLine = line + DELETED_LINE_TAG;
        codeLines.set(index, markedLine);
    }

    public static SourceFile createSourceFilewithDeletedLines(String filePath, int numDeletedLines) {
        Path path = Paths.get(filePath);
        return new SourceFile(
            path.getFileName().toString(),
            path.toString(),
            0,  // physicalLOC will be set later
            0,  // numOfMethods will be set later
            0,  // addedLines
            numDeletedLines,  // deletedLines
            ""  // status will be set later
        );
    }

    private static List<String> cleanLines(List<String> lines) {
        return lines.stream()
            .map(String::trim)
            .filter(line -> !line.isEmpty())                // eliminar líneas en blanco
            .filter(line -> !line.startsWith("//"))         // eliminar comentarios de línea
            .filter(line -> !line.startsWith("/*") && !line.startsWith("*") && !line.endsWith("*/")) // filtrar básicos de bloque
            .collect(Collectors.toList());
    }
}
