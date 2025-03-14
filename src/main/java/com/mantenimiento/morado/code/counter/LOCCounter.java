package com.mantenimiento.morado.code.counter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.util.Constants;

/**
 * The {@code LOCCounter} class provides functionality for counting the
 * physical and logical lines of code (LOC) in a Java source file.
 *
 * <p>
 * Physical LOC counts non-empty lines of code excluding ignorable lines such as blank lines
 * or comments, while logical LOC counts statements (lines ending with a semicolon or an opening brace)
 * excluding comments and ignorable lines.
 * </p>
 * <p>
 * This class uses helper methods to determine if a line is part of a block comment,
 * should be ignored, or qualifies as a logical line.
 * </p>
 * @author Rubén Alvarado
 * @author Reynaldo Couoh
 * @version 1.0.0
 */
public class LOCCounter {
    private static int logicalLOC = 0;
    private static int physicalLOC = 0;

    /**
     * Counts both logical and physical lines of code from the specified file and
     * creates a {@code SourceFile} object with the file's name, LOC counts, and status.
     *
     * @param filePath The path of the Java source file to be analyzed.
     * @return A {@code SourceFile} object containing the file's name, logical LOC, physical LOC,
     * and the Java file status constant from {@link Constants}.
     */
    public static SourceFile countLOC(String filePath) {
        Path path = Paths.get(filePath);

        try {
            List<String> codeLines = SourceFile.getAllLinesFromFile(filePath);

            setLogicalLOC(countLogicalLOC(codeLines));
            setPhysicalLOC(countPhysicalLOC(codeLines));
        } catch (IOException ioException) {
            System.err.println("Error while processing file: " + ioException.getMessage());
        }

        return new SourceFile(
            path.getFileName().toString(),
            logicalLOC,
            physicalLOC,
            Constants.JAVA_FILE_STATUS_OK
        );
    }

    /**
     * Counts the physical lines of code in the provided list of code lines.
     * Physical LOC includes all non-ignorable lines outside of block comments.
     *
     * @param codeLines a list of strings representing lines of code from the source file
     * @return the number of physical lines of code
     */
    private static int countPhysicalLOC(List<String> codeLines) {
        int physicalLOC = 0;
        boolean inBlockComment = false;

        for (String line : codeLines) {
            String trimmed = line.trim();

            if (inBlockComment) {
                if (endsBlockComment(trimmed)) {
                    inBlockComment = false;
                }
                continue;
            }

            if (startsBlockComment(trimmed)) {
                inBlockComment = true;
                continue;
            }

            if (!isIgnorableLine(trimmed)) {
                physicalLOC++;
            }
        }

        return physicalLOC;
    }

    /**
     * Counts the logical lines of code in the provided list of code lines.
     * Logical LOC includes lines that represent statements, typically ending with a semicolon or an opening brace,
     * excluding ignorable lines and comments.
     *
     * @param codeLines a list of strings representing lines of code from the source file
     * @return the number of logical lines of code
     */
    private static int countLogicalLOC(List<String> codeLines) {
        int logicalLOC = 0;
        boolean inBlockComment = false;

        for (String line : codeLines) {
            String trimmed = line.trim();

            if (inBlockComment) {
                if (endsBlockComment(trimmed)) {
                    inBlockComment = false;
                }
                continue;
            }

            if (startsBlockComment(trimmed)) {
                inBlockComment = true;
                continue;
            }

            if (!isIgnorableLine(trimmed) && isLogicalLine(trimmed)) {
                logicalLOC++;
            }
        }

        return logicalLOC;
    }

    /**
     * Checks whether the given line marks the end of a block comment.
     *
      * @param line The trimmed line of code.
     * @return {@code true} if the line ends with {@code * /}, otherwise {@code false}.
     */
    private static boolean endsBlockComment(String line) {
        return line.endsWith("*/");
    }

    /**
     * Checks whether the given line marks the start of a block comment.
     *
     * @param line The trimed line of code.
     * @return {@code true} if the line starts with {@code / *}, otherwise {@code false}.
     */
    private static boolean startsBlockComment(String line) {
        return line.startsWith("/*");
    }

    /**
     * Determines if the given line should be ignored when counting lines of code.
     * Ignorable lines include empty lines and lines that start with single-line comment markers.
     *
     * @param line the trimmed line of code
     * @return {@code true} if the line is empty, starts with "//", or starts with "*", otherwise {@code false}.
     */
    private static boolean isIgnorableLine(String line) {
        return line.isEmpty() || line.startsWith("//") || line.startsWith("*");
    }

    /**
     * Determines if the given line qualifies as a logical line of code.
     * A logical line is defined as one that ends with a semicolon or an opening brace.
     *
     * @param line the trimmed line of code
     * @return {@code true} if the line ends with ";" or "{", otherwise {@code false}.
     */
    private static boolean isLogicalLine(String line) {
        return line.matches(".*\\b(class|interface|if|while|switch|for|try|do|public|private|protected|static)\\b.*\\{\\s*$");
    }

    /**
     * Sets the total count of logical lines of code.
     *
     * @param _logicalLOC the logical LOC count to set
     */
    private static void setLogicalLOC(int _logicalLOC) {
        logicalLOC = _logicalLOC;
    }

    /**
     * Sets the total count of physical lines of code.
     *
     * @param _physicalLOC the physical LOC count to set
     */
    private static void setPhysicalLOC(int _physicalLOC) {
        physicalLOC = _physicalLOC;
    }
}
