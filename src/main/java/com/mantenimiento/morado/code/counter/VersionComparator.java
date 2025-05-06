package com.mantenimiento.morado.code.counter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mantenimiento.morado.code.model.JavaProject;
import com.mantenimiento.morado.code.model.SourceFile;

/**
 * Compares two versions of a Java project to identify added and deleted lines in source files.
 * It requires a list containing exactly two {@link JavaProject} objects: the old and the new version.
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
            }
        }
    }

    /**
     * Compares a single source file from the old and new versions to find added and deleted lines.
     * It uses {@link DeletedLinesAnalyzer} and {@link AddedLinesAnalyzer} to perform the comparison
     * and mark the lines in output files. It also updates the added and deleted line counts in
     * the respective {@link SourceFile} objects.
     *
     * @param oldFile The {@link SourceFile} from the old version.
     * @param newFile The {@link SourceFile} from the new version.
     */
    private static void processFileComparison(SourceFile oldFile, SourceFile newFile) {
        try {
            DeletedLinesAnalyzer deleteAnalyzer = new DeletedLinesAnalyzer(
                oldFile.getAllLinesFromFile(), newFile.getAllLinesFromFile()
            );
            deleteAnalyzer.markAndWriteDeleted(oldFile.getFilename());
            oldFile.setDeletedLines(deleteAnalyzer.getDeletedLineCount());

            AddedLinesAnalyzer addAnalyzer = new AddedLinesAnalyzer(
                oldFile.getAllLinesFromFile(), newFile.getAllLinesFromFile()
            );
            addAnalyzer.markAndWriteAdded(newFile.getFilename());
            newFile.setAddedLines(addAnalyzer.getAddedLineCount());

        } catch (IOException ioException) {
            System.out.println("Error while reading file: " + ioException.getMessage());
        }
    }
}
