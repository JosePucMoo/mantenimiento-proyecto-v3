package com.mantenimiento.morado.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Represents a Java source file and tracks basic metrics and state:
 * <ul>
 *   <li>Filename and file path</li>
 *   <li>Physical lines of code (LOC)</li>
 *   <li>Number of methods</li>
 *   <li>Count of added and deleted lines (after comparison)</li>
 *   <li>Arbitrary status flag (e.g., “processed”, “error”)</li>
 * </ul>
 *
 *
 * @author Ruben Alvarado
 * @author Diana Vazquez
 * @author Aaron Graniel
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
     * Creates a new SourceFile wrapper. All numeric metrics start at zero,
     * status is empty.
     *
     * @param filename the file’s name (e.g. “MyClass.java”)
     * @param filePath the full path to the file on disk
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
     * Reads the entire file into a list of lines.
     *
     * @return list of all lines in the file
     * @throws IOException if there’s an I/O problem reading the file
     */
    public List<String> getAllLinesFromFile() throws IOException {
        return Files.readAllLines(Paths.get(this.filePath));
    }

    // — Getters and setters below — //

    public String getFilename() {
        return filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getPhysicalLOC() {
        return physicalLOC;
    }

    public void setPhysicalLOC(int physicalLOC) {
        this.physicalLOC = physicalLOC;
    }

    public int getNumOfMethods() {
        return numOfMethods;
    }

    public void setNumOfMethods(int numOfMethods) {
        this.numOfMethods = numOfMethods;
    }

    public int getAddedLines() {
        return addedLines;
    }

    public void setAddedLines(int addedLines) {
        this.addedLines = addedLines;
    }

    public int getDeletedLines() {
        return deletedLines;
    }

    public void setDeletedLines(int deletedLines) {
        this.deletedLines = deletedLines;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
