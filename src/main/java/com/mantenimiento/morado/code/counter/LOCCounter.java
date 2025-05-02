package com.mantenimiento.morado.code.counter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.util.Constants;

/**
 * The {@code LOCCounter} class provides functionality for counting the
 * physical lines of code (LOC), and the number of methods in a Java source file.
 *
 * <p>
 * Physical LOC counts non-empty lines of code excluding ignorable lines such as blank lines
 * or comments. The method count does not consider abstract methods.
 * </p>
 * <p>
 * This class uses helper methods to determine if a line is part of a block comment,
 * should be ignored, or qualifies as a logical line, a method, or an abstract method.
 * </p>
 * @author Rubén Alvarado
 * @author Reynaldo Couoh
 * @author Diana Vazquez
 * @version 2.0.0
 */
public class LOCCounter {
    private static int physicalLOC = 0;
    private static int numOfMethods = 0;

    /**
     * Counts the physical and class method lines of code in the specified file and
     * creates a {@code SourceFile} object with the file's name, LOC counts, and status.
     *
     * @param filePath The path of the Java source file to be analyzed.
     * @return A {@code SourceFile} object containing the file's name, logical LOC,
     * number of methods and the Java file status constant from {@link Constants}.
     */
    public static SourceFile countLOC(String filePath) {
        Path path = Paths.get(filePath);

        try {
            List<String> codeLines = SourceFile.getAllLinesFromFile(filePath);
            setPhysicalLOC(countPhysicalLOC(codeLines));
            setNumOfMethods(countNumOfMethods(codeLines));
        } catch (IOException ioException) {
            System.err.println("Error while processing file: " + ioException.getMessage());
        }

        return new SourceFile(
            path.getFileName().toString(),
            path.toString(),
            physicalLOC,
            numOfMethods,
            0,
            0,
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
    * Counts the number of methods in a class.
    * Abstract methods are not counted as methods.
    *
    * @param codeLines: A list of strings representing lines of code in the source file.
    * @return: The number of methods in a class.
    */
    private static int countNumOfMethods(List<String> codeLines) {
        int numOfMethods = 0;
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

            if (!isIgnorableLine(trimmed) && isAbstractMethodLine(trimmed)) {
                continue;
            }

            if (!isIgnorableLine(trimmed) && isMethodLine(trimmed)) {
                numOfMethods++;
            }
        }

        return numOfMethods;
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
    * Determines whether the given line is considered a method.
    *
    * @param line the line of code to trim
    * @return {@code true} if the line is a method, otherwise {@code false}.
    */
    private static boolean isMethodLine(String line) {
        return line.matches("^(public|private|protected)\\s+[a-zA-Z\\s]*\\s*[\\w<>\\[\\],]*\\s*\\w+\\s*\\(.*\\)?\\s*.*\\{?\\s*(//.*)?$");
    }

    /**
    * Determines whether the given line is considered an abstract method.
    *
    * @param line the line of code to trim
    * @return {@code true} if the line is an abstract method, otherwise {@code false}.
    */
    private static boolean isAbstractMethodLine(String line) {
        return line.matches("^(public|private|protected)\\s(abstract)\\s+[\\w<>\\[\\],]+\\s+\\w+\\s*\\(.*\\)?\\s*(//.*)?");
    }

    /**
     * Sets the total count of physical lines of code.
     *
     * @param _physicalLOC the physical LOC count to set
     */
    private static void setPhysicalLOC(int _physicalLOC) {
        physicalLOC = _physicalLOC;
    }

    /**
    * Sets the total number of methods in a class
    *
    * @param _physicalLOC the number of methods to set
    */
    private static void setNumOfMethods(int _numOfMethods) {
        numOfMethods = _numOfMethods;
    }
}