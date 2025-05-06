package com.mantenimiento.morado.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

/**
 * Utility class for managing file operations related to line differences.
 * <p>
 * This class allows writing lines to subfolders based on tags such as
 * "added" or "deleted", and ensures that each subfolder is recreated
 * cleanly before writing.
 * </p>
 * 
 * <p>Generated files are stored under the {@code comparison_results} root folder.</p>
 */
public class FileHelper {

    /** Root folder where comparison result files are stored. */
    private static final String ROOT_FOLDER = "comparison_results";

    /** Subfolder for lines that were removed (tag: "deleted"). */
    private static final String REMOVED_FOLDER = "deleted_lines";

    /** Subfolder for lines that were added (tag: "added"). */
    private static final String ADDED_FOLDER = "added_lines";

    /**
     * Writes the given lines to a file within a subfolder, determined by the tag.
     * <p>
     * If the subfolder already exists, it will be deleted and recreated before writing.
     * </p>
     *
     * @param lines    the list of strings to be written to the file
     * @param fileName the name of the output file (e.g., {@code "MyClass.java"})
     * @param tag      either {@code "deleted"} or {@code "added"}, used to select the subfolder
     * @throws IllegalArgumentException if the tag is not {@code "deleted"} or {@code "added"}
     */
    public static void writeLinesByTag(List<String> lines, String fileName, String tag) {
        String subfolder = switch (tag) {
            case "deleted" -> REMOVED_FOLDER;
            case "added"   -> ADDED_FOLDER;
            default -> throw new IllegalArgumentException("Invalid tag: use 'deleted' or 'added'.");
        };
        writeToSubfolder(lines, subfolder, fileName);
    }

    /**
     * Writes the specified lines to a file inside the given subfolder under the root directory.
     * <p>
     * If the subfolder already exists, it is deleted recursively and recreated.
     * </p>
     *
     * @param lines         list of lines to be written
     * @param subfolder     the name of the subfolder under the root directory
     * @param outputFileName the name of the file to create
     */
    private static void writeToSubfolder(List<String> lines, String subfolder, String outputFileName) {
        try {
            Path dir = Paths.get(ROOT_FOLDER, subfolder);
            
            if (Files.exists(dir)) {
                deleteDirectoryRecursively(dir);
            }

            Files.createDirectories(dir);

            Path filePath = dir.resolve(outputFileName);
            Files.write(filePath, lines);
            System.out.println("File saved at: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    /**
     * Recursively deletes the given directory and all its contents.
     *
     * @param path the path to the directory to delete
     * @throws IOException if an I/O error occurs during deletion
     */
    private static void deleteDirectoryRecursively(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        }
    }
}