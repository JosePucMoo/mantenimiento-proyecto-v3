package com.mantenimiento.morado.code.counter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.code.syntax.SyntaxAnalyzer;
import com.mantenimiento.morado.util.Constants;

/**
 * Analyzes Java source files in a given directory by scanning for files,
 * checking their syntax, counting lines of code (LOC), and printing the results
 * in a formatted table.
 *
 * @author Ruben Alvarado
 * @author Reynaldo Couoh
 * @author Diana Vazquez
 * @version 2.0.0
 */
public class SourceFileAnalyzer {
    private final String directoryPath;

    /**
     * Constructs a new {@code SourceFileAnalyzer} with the specified directory path
     *
     * @param directoryPath The path to the directory containing Java source files.
     */
    public SourceFileAnalyzer(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    /**
     * Analyzes the specified path to process Java source files and count their lines of code (LOC).
     * <p>
     * The method determines whether the given path is a file or a directory and processes it accordingly:
     * </p>
     * <ul>
     *   <li><b>If the path is a Java source file</b>: It extracts the file and analyzes its LOC.</li>
     *   <li><b>If the path is a directory</b>: It scans for subdirectories and processes all Java files found within them.</li>
     *   <li><b>If the path is invalid</b>: A message is printed indicating that the path is neither a file nor a directory.</li>
     * </ul>
     * <p>
     * The results are displayed in a tabular format in the console.
     * </p>
     */
    public void analyzePath() {
        DirectoryScanner scanner = new DirectoryScanner(directoryPath);
        if (scanner.isFile(directoryPath)) {
            List<String> javaFilesPaths = scanner.getJavaFiles(Paths.get(directoryPath));
            printHeader(); 
            analyzeJavaFiles("", javaFilesPaths);
        } else if (scanner.isDirectory(directoryPath)) {
            List<Path> javaDirectoriesPaths = scanner.getSubdirectories();
            printHeader();
            analyzeDirectory(javaDirectoriesPaths, scanner);
        } else {
            System.out.println("The specified path is not a valid file or directory.");
        }
    }

    /**
     * Scans the directory for Java source files and subdirectories. When there are no subdirectories, it analyzes every file, if there are, analyzes each one.
     * <p>
     * If the directory has no subdirectories:
     * </p>
     * <ul>
     *   <li>Get every java file and count them.</li>
     * </ul>
     * <p>
     * Otherwise, analyze every subdirectory and scan for its files.
     * </p>
     * <p>
     * The results are printed to the console in a tabular format.
     * </p>
     */
    private void analyzeDirectory(List<Path> javaSubdirectoriesPaths, DirectoryScanner scanner) {
        int totalPhysicalLOC = 0;
        for (Path subdirectoryPath : javaSubdirectoriesPaths) {
            List<String> javaFilesPaths = scanner.getJavaFiles(subdirectoryPath);
            totalPhysicalLOC += analyzeJavaFiles(subdirectoryPath.getFileName().toString(), javaFilesPaths);
        }

        if (totalPhysicalLOC > 0) {
            printTotalProyectLOC(totalPhysicalLOC + "");
        }
    }

    /**
     * Analyzes a list of Java files, checks if they are well-written, and counts their physical lines of code.
     * <p>
     * If the file is well-written:
     * </p>
     * <ul>
     *   <li>Counts LOC, marks it as having no class if not.</li>
     * </ul>
     * <p>
     * If the file is not well-written, it is marked with an error status.
     * </p>
     * <p>
     * The results, including file details and total physical LOC, are printed in a tabular format.
     * </p>
     * 
     * @param directoryName The name of the directory containing the Java files.
     * @param javaFilesPaths The list of paths to the Java files.
     */
    private int analyzeJavaFiles(String directoryName, List<String> javaFilesPaths){
        int totalPhysicalLOC = 0;
        for (String filePath : javaFilesPaths) {
            SourceFile file;
            if (SyntaxAnalyzer.isJavaFileWellWritten(filePath)) {
                file = LOCCounter.countLOC(filePath);
                totalPhysicalLOC += file.physicalLOC();
                if (!SyntaxAnalyzer.isClassJavaFile(filePath)) {
                    file = getNoClassFile(filePath, file.physicalLOC());
                }              
            } else {
                file = getBadSourceFile(filePath);
            }
            printDetails(file, directoryName);
            directoryName = "";
        }
        
        if (totalPhysicalLOC > 0) {
            printTotalProgramLOC(totalPhysicalLOC + "");
            return totalPhysicalLOC;
        }

        return 0;
    }

    /**
     * Prints the header for the LOC analysis results table.
     * <p>
     * The header includes the columns: "Program", "Logical LOC", "Physical LOC", and "Status".
     * </p>
     */
    private void printHeader() {
        System.out.printf("%-18s %-30s %-18s %-18s %-18s %-10s%n", "Program", "Class", "Number of methods", "Physical LOC", "Total physical LOC", "Status");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Prints the details of a Java source file.
     * <p>
     * The details include the file name, logical LOC, physical LOC, and status.
     * </p>
     * @param file the {@link SourceFile} object containing the filename, logical LOC,
     *             physical LOC, and status to be printed
     * @see SourceFile
     */
    private void printDetails(SourceFile file, String directoryName) {
        System.out.printf(
            "%-18s %-30s %-18s %-18s %-18s %-10s%n",
            directoryName,
            file.filename().replaceFirst("\\.java$", ""),
            file.numOfMethods(),
            file.physicalLOC(),
            "",
            file.status()
        );
    }

    /**
     * Prints the total physical lines of code (LOC) of a program in a formatted table.
     * 
     * @param totalPhysicalLOC The total number of physical LOC to print.
     */
    private void printTotalProgramLOC(String totalPhysicalLOC) {
        System.out.printf(
            "%-18s %-30s %-18s %-18s %-18s %-10s%n",
            "",
            "",
            "",
            "",
            totalPhysicalLOC,
            ""
        );
    }

    /**
     * Prints the total physical lines of code (LOC) of a proyect in a formatted table.
     * 
     * @param totalPhysicalLOC The total number of physical LOC to print.
     */
    private void printTotalProyectLOC(String totalPhysicalLOC) {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.printf(
            "%-18s %-30s %-18s %-18s %-18s %-10s%n",
            "Total Lines",
            "",
            "",
            "",
            totalPhysicalLOC,
            ""
        );
    }


    /**
     * Creates a {@code SourceFile} object representing a Java file that failed syntax analysis.
     * <p>
     * The returned object has zero logical and physical LOC and a status indicating an error.
     * </p>
     * @param filepath the path to the Java file that is considered not well written
     * @return a {@link SourceFile} instance with error status
     */
    private SourceFile getBadSourceFile(String filepath) {
        Path file = Paths.get(filepath);

        return new SourceFile(
            file.getFileName().toString(),
            0,
            0,
            Constants.JAVA_FILE_STATUS_ERROR
        );
    }

    /**
     * Creates a SourceFile object for a Java file .
     * This gets relevant info of the class to print.
     * 
     * @param filepath The path to the Java file.
     * @return A SourceFile object with a status of "no class".
     */
    private SourceFile getNoClassFile(String filepath, int physicalLOC) {
        Path file = Paths.get(filepath);

        return new SourceFile(
            file.getFileName().toString(),
            physicalLOC,
            0,
            Constants.JAVA_FILE_STATUS_NO_CLASS
        );
    }

}
