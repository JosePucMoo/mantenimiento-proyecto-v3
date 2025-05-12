package com.mantenimiento.test.test_cases;

import com.mantenimiento.morado.code.counter.FileFormatter;
import com.mantenimiento.morado.code.model.SourceFile;

import java.io.IOException;
import java.util.List;

public class CP_004 {
    public static void main(String[] args) {
        String path = "src\\main\\java\\com\\mantenimiento\\test\\test_cases\\docs\\DocsCP_004.txt";

        try {
            SourceFile file = new SourceFile(path);
            FileFormatter formatter = new FileFormatter();

            // Formatea y guarda las líneas modificadas
            formatter.formatFile(null, file);

            // Imprime el resultado para validación manual
            List<String> resultLines = file.getAllLinesFromFile();
            System.out.println("Resultado tras aplicar formatFile:");
            for (String line : resultLines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("CP_004: Error al procesar el archivo: " + e.getMessage());
        }
    }
}
