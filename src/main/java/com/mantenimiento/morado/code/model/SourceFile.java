package com.mantenimiento.morado.code.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Represents a source file with its name, logical lines of code, physical lines of code,
 * number of methods, added/deleted lines, and a status indicating its state.
 * <p>
 * This record is used to encapsulate basic information about a Java source file.
 * It provides a static method to read all lines from a file.
 * </p>
 *
 * @author Ruben Alvarado
 * @author Diana Vazquez
 * @author Fernando Joachin
 * @version 2.1.0
 *
 * @param filename      the name of the source file
 * @param physicalLOC   the number of physical lines of code in the file
 * @param numOfMethods  the number of methods in the file
 * @param addedLines    the number of lines added to the file
 * @param deletedLines  the number of lines deleted from the file
 * @param status        the status of the source file (e.g., "well-written", "error", etc.)
 */
public record SourceFile(
    String filename,
    int physicalLOC,
    int numOfMethods,
    int addedLines,
    int deletedLines,
    String status
){
    /**
     * Reads all lines from the specified file.
     *
     * <p>This method uses {@link java.nio.file.Files#readAllLines(java.nio.file.Path)}.
     * to read the file content and return it as a list of strings.</p>
     *
     * @param filepath the path to the file
     * @return a {@code List<String>} containing all lines from the file
     * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read
     */
    public static List<String> getAllLinesFromFile(String filepath) throws IOException {
        return Files.readAllLines(Paths.get(filepath));
    }
}
