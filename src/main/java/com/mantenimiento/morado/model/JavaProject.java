package com.mantenimiento.morado.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Java project composed of multiple source files with metrics tracking.
 * 
 * <p>This mutable class maintains project information including:
 * <ul>
 *   <li>Project name</li>
 *   <li>Collection of source files with their metrics</li>
 *   <li>Aggregate metrics calculations</li>
 * </ul>
 * </p>
 *
 * <p>The class provides methods to:
 * <ul>
 *   <li>Access and modify project properties</li>
 *   <li>Calculate total metrics across all source files</li>
 *   <li>Update individual source files</li>
 * </ul>
 * </p>
 *
 * @author Fernando Joachin
 * @version 1.0.0
 */
public final class JavaProject {
    private String projectName;
    private List<SourceFile> sourceFiles;
    
    /**
     * Constructs a new JavaProject with the specified name and source files.
     * Makes a defensive copy of the source files list to prevent external modification.
     *
     * @param projectName the name of the Java project (cannot be null)
     * @param sourceFiles the list of source files belonging to this project (cannot be null)
     * @throws NullPointerException if either projectName or sourceFiles is null
     */
    public JavaProject(String projectName, List<SourceFile> sourceFiles) {
        this.projectName = Objects.requireNonNull(projectName, "Project name cannot be null");
        this.sourceFiles = new ArrayList<>(Objects.requireNonNull(sourceFiles, "Source files list cannot be null"));
    }

    /**
     * Returns the name of this project.
     *
     * @return the project name (never null)
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * Sets the name of this project.
     *
     * @param projectName the new project name (cannot be null)
     * @throws NullPointerException if projectName is null
     */
    public void setProjectName(String projectName) {
        this.projectName = Objects.requireNonNull(projectName, "Project name cannot be null");
    }

    /**
     * Returns a copy of the list of source files in this project.
     * The returned list is a defensive copy to maintain encapsulation.
     *
     * @return a new List containing all source files (never null)
     */
    public List<SourceFile> getSourceFiles() {
        return new ArrayList<>(this.sourceFiles);
    }

    /**
     * Replaces the current source files with the provided collection.
     * Makes a defensive copy of the provided list.
     *
     * @param sourceFiles the new collection of source files (cannot be null)
     * @throws NullPointerException if sourceFiles is null
     */
    public void setSourceFiles(List<SourceFile> sourceFiles) {
        this.sourceFiles = new ArrayList<>(Objects.requireNonNull(sourceFiles, "Source files list cannot be null"));
    }

    /**
     * Calculates the total number of physical lines of code (LOC) across all source files.
     * This includes all lines, regardless of whether they contain code, comments, or are blank.
     *
     * @return the sum of physical LOC from all source files
     */
    public int getTotalPhysicalLOC() {
        return sourceFiles.stream()
                .mapToInt(SourceFile::getPhysicalLOC)
                .sum();
    }

    /**
     * Calculates the total number of methods declared across all source files.
     *
     * @return the sum of methods from all source files
     */
    public int getTotalMethods() {
        return sourceFiles.stream()
                .mapToInt(SourceFile::getNumOfMethods)
                .sum();
    }

    /**
     * Calculates the total number of lines added across all source files.
     * Typically used for version control/diff statistics.
     *
     * @return the sum of added lines from all source files
     */
    public int getTotalAddedLines() {
        return sourceFiles.stream()
                .mapToInt(SourceFile::getAddedLines)
                .sum();
    }

    /**
     * Calculates the total number of lines deleted across all source files.
     * Typically used for version control/diff statistics.
     *
     * @return the sum of deleted lines from all source files
     */
    public int getTotalDeletedLines() {
        return sourceFiles.stream()
                .mapToInt(SourceFile::getDeletedLines)
                .sum();
    }
}