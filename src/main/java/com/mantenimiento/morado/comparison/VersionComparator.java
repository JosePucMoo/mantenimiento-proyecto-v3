package com.mantenimiento.morado.comparison;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mantenimiento.morado.analyzer.AddedLinesAnalyzer;
import com.mantenimiento.morado.analyzer.DeletedLinesAnalyzer;
import com.mantenimiento.morado.model.JavaProject;
import com.mantenimiento.morado.model.SourceFile;

/**
 * Compares two versions of a Java project to identify added and deleted lines in source files.
 * It requires a list containing exactly two {@link JavaProject} objects: the old and the new version.
 * 
 * <p>After running, each {@link SourceFile} in the projects will have its
 * added/deleted line counts updated and a file written with tags.</p>
 * 
 * @author Aaron Graniel
 * @author Fernando Joachin
 * @version 1.0.0
 */
public class VersionComparator {

    /**
     * Compares the source files between two versions of a Java project.
     * For each file present in both versions, it detects added and deleted lines.
     *
     * @param projectsToCompare A list containing two {@link JavaProject} objects.
     * The first is the old version, the second is the new.
     * @throws IllegalArgumentException if the list does not contain exactly two projects.
     */
    public static void compareVersions(List<JavaProject> projectsToCompare) {
        JavaProject oldVersion = projectsToCompare.get(0);
        JavaProject newVersion = projectsToCompare.get(1);

        Map<String, SourceFile> oldVersionFiles = new HashMap<>();
        for (SourceFile sourceFile : oldVersion.getSourceFiles()) {
            oldVersionFiles.put(sourceFile.getFilename(), sourceFile);
        }

        for (SourceFile newFile : newVersion.getSourceFiles()) {
            String newfilename = newFile.getFilename();
            if (oldVersionFiles.containsKey(newfilename)) {
                SourceFile oldFile = oldVersionFiles.get(newfilename);
                processFileComparison(oldFile, newFile);
                oldVersionFiles.remove(newfilename);
            } else {
                try {
                    processAddedLines(null, newFile);
                } catch (IOException ioException) {
                    System.out.println("Error while reading file: " + ioException.getMessage());
                }
            }
        }

        for (SourceFile oldSourceFile : oldVersionFiles.values()) {
            try {
                processDeletedLines(oldSourceFile, null);
            } catch (IOException ioException) {
                System.out.println("Error while reading file: " + ioException.getMessage());
            }
        }
    }

    /**
     * Compares a single source file from the old and new versions to find added and deleted lines.
     * It delegates the comparison to helper methods that analyze deleted and added lines separately.
     * It updates the line counts in the respective {@link SourceFile} objects.
     *
     * @param oldFile The {@link SourceFile} from the old version.
     * @param newFile The {@link SourceFile} from the new version.
     */
    private static void processFileComparison(SourceFile oldFile, SourceFile newFile) {
        try {
            processDeletedLines(oldFile, newFile);
            processAddedLines(oldFile, newFile);
        } catch (IOException ioException) {
            System.out.println("Error while reading file: " + ioException.getMessage());
        }
    }

    /**
     * Analyzes and marks deleted lines by comparing the content of the old and new files.
     * Writes the marked deleted lines to the output file and updates the deleted line count
     * in the {@link SourceFile} representing the old version.
     *
     * @param oldFile The {@link SourceFile} from the old version.
     * @param newFile The {@link SourceFile} from the new version or {@code null} if not available.
     * @throws IOException if there is an error reading from or writing to the files.
     */
    private static void processDeletedLines(SourceFile oldFile, SourceFile newFile) throws IOException {
        List<String> newLines = (newFile != null) ? newFile.getAllLinesFromFile() : Collections.emptyList();
        DeletedLinesAnalyzer deleteAnalyzer = new DeletedLinesAnalyzer(
            oldFile.getAllLinesFromFile(), newLines
        );
        deleteAnalyzer.markAndWriteDeleted(oldFile.getFilename());
        oldFile.setDeletedLines(deleteAnalyzer.getDeletedLineCount());
    }

    /**
     * Analyzes and marks added lines by comparing the content of the old and new files.
     * Writes the marked added lines to the output file and updates the added line count
     * in the {@link SourceFile} representing the new version.
     *
     * @param oldFile The {@link SourceFile} from the old version or {@code null} if not available.
     * @param newFile The {@link SourceFile} from the new version.
     * @throws IOException if there is an error reading from or writing to the files.
     */
    private static void processAddedLines(SourceFile oldFile, SourceFile newFile) throws IOException {
        List<String> oldLines = (oldFile != null) ? oldFile.getAllLinesFromFile() : Collections.emptyList();
        AddedLinesAnalyzer addAnalyzer = new AddedLinesAnalyzer(
            oldLines, newFile.getAllLinesFromFile()
        );
        addAnalyzer.markAndWriteAdded(newFile.getFilename());
        newFile.setAddedLines(addAnalyzer.getAddedLineCount());
    }

}
