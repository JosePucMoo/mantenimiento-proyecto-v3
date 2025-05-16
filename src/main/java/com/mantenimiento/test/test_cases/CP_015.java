package com.mantenimiento.test.test_cases;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.mantenimiento.morado.model.SourceFile;
import com.mantenimiento.morado.util.FileFormatter;
import com.mantenimiento.morado.util.FileHelper;

public class CP_015 {
    public static void main(String[] args) {
        Path path = Paths.get(
            "src\\main\\java\\com\\mantenimiento\\test\\test_cases\\docs\\DocsCP_015\\DocCP_015.java"
        );

        try {
            Path dir = Paths.get(FileHelper.ROOT_FORMATTED_FOLDER);
            FileHelper.deleteDirectoryRecursively(dir);
        } catch (Exception e) {
            System.err.println("Error deleting file: " + e.getMessage());
        }

        try {
            SourceFile file = new SourceFile(path.getFileName().toString(), path.toString());
            FileFormatter formatter = new FileFormatter();

            // Formatea y guarda las líneas modificadas
            formatter.formatFile(file);
        } catch (IOException e) {
            System.err.println("CP_015: Error al procesar el archivo: " + e.getMessage());
        }
    }
}
