package com.mantenimiento.morado.code.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Represents a source file with its name, number of logical lines of code, physical lines of code,
 * methods, added/deleted lines, and a status indicating its state.
 * <p>
 * This class is used to encapsulate basic information about a Java source file.
 * It provides a static method to read all lines from a file.
 * </p>
 *
 * @author Ruben Alvarado
 * @author Diana Vazquez
 * @author Fernando Joachin
 * @version 2.1.0
*/
public class SourceFile {

    private final String filename;
    private final String filePath;
    private int physicalLOC;
    private int numOfMethods;
    private int addedLines;
    private int deletedLines;
    private String status;

    /**
     * Constructs a new {@code SourceFile} with the given filename and path.
     * Other attributes are initialized to zero or empty and can be set later.
     * 
     * @param filename the name of the source file
     * @param filePath the path to the source file
     */
    public SourceFile(String filename, String filePath) {
        this.filename     = filename;
        this.filePath     = filePath;
        this.physicalLOC  = 0;
        this.numOfMethods = 0;
        this.addedLines   = 0;
        this.deletedLines = 0;
        this.status       = "";
    }

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

    // Getters

    public String getFilename() {
        return filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getPhysicalLOC() {
        return physicalLOC;
    }

    public int getNumOfMethods() {
        return numOfMethods;
    }

    public int getAddedLines() {
        return addedLines;
    }

    public int getDeletedLines() {
        return deletedLines;
    }

    public String getStatus() {
        return status;
    }

    // Setters

    public void setPhysicalLOC(int physicalLOC) {
        this.physicalLOC = physicalLOC;
    }

    public void setNumOfMethods(int numOfMethods) {
        this.numOfMethods = numOfMethods;
    }

    public void setAddedLines(int addedLines) {
        this.addedLines = addedLines;
    }

    public void setDeletedLines(int deletedLines) {
        this.deletedLines = deletedLines;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
