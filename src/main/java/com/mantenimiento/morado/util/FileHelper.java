package com.mantenimiento.morado.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileHelper {

    private static final String ROOT_FOLDER = "comparison_results";
    private static final String REMOVED_FOLDER = "deleted_lines";
    private static final String ADDED_FOLDER = "added_lines";

    /**
     * Writes the given lines into the appropriate folder based on the tag.
     *
     * @param lines     Lines to write
     * @param fileName  Name of the file (e.g., "MyClass.java")
     * @param tag       Must be either "deleted" or "added"
     */
    public static void writeLinesByTag(List<String> lines, String fileName, String tag) {
        String subfolder;

        switch (tag) {
            case "deleted" -> {
                subfolder = REMOVED_FOLDER;
            }
            case "added" -> {
                subfolder = ADDED_FOLDER;
            }
            default -> throw new IllegalArgumentException("Invalid tag. Use 'deleted' or 'added'.");
        }

        writeToSubfolder(lines, subfolder, fileName);
    }

    private static void writeToSubfolder(List<String> lines, String subfolder, String outputFileName) {
        try {
            Path dir = Paths.get(ROOT_FOLDER, subfolder);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }

            Path filePath = dir.resolve(outputFileName);
            Files.write(filePath, lines);
            System.out.println("File saved at: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
