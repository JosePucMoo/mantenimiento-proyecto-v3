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
     * Scans the directory for Java source files and analyzes their lines of code.
     * <p>
     * For each Java file found:
     * </p>
     * <ul>
     *   <li>If the file is well written according to the syntax analyzer, its LOC is counted.</li>
     *   <li>Otherwise, it is marked as having an error.</li>
     * </ul>
     * <p>
     * The results are printed to the console in a tabular format.
     * </p>
     */
    public void analyzePath() {
        DirectoryScanner scanner = new DirectoryScanner(directoryPath);
        if (scanner.isFile(directoryPath)) {
            List<String> javaFilesPaths = scanner.getJavaFiles(Paths.get(directoryPath));
            printHeader(); 
            analyzeJavaFiles("", javaFilesPaths);
        } else{
            analyzeDirectory(scanner);
        }
    }

    private void analyzeDirectory(DirectoryScanner scanner){
        List<Path> javaSubdirectoriesPaths = scanner.getSubdirectories();
        printHeader();

        if (javaSubdirectoriesPaths.isEmpty()) {
            String directoryName = scanner.getDirectoryName();
            List<String> javaFilesPaths = scanner.getJavaFiles(Paths.get(directoryPath)); 
            analyzeJavaFiles(directoryName, javaFilesPaths);
        } else {
            for (Path subdirectoryPath : javaSubdirectoriesPaths) {
                List<String> javaFilesPaths = scanner.getJavaFiles(subdirectoryPath);
                analyzeJavaFiles(subdirectoryPath.getFileName().toString(), javaFilesPaths);
            }
        }
    }

    private void analyzeJavaFiles(String directoryName, List<String> javaFilesPaths){
        int totalPhysicalLOC = 0;
        for (String filePath : javaFilesPaths) {
            SourceFile file;
            if (SyntaxAnalyzer.isJavaFileWellWritten(filePath)) {
                if (!SyntaxAnalyzer.isClassJavaFile(filePath)) {
                    file = getNoClassFile(filePath);
                } else {
                    file = LOCCounter.countLOC(filePath);
                    totalPhysicalLOC += file.physicalLOC();
                }                
            } else {
                file = getBadSourceFile(filePath);
            }

            printDetails(file, directoryName);
            directoryName = "";
        }
        
        if (totalPhysicalLOC > 0) {
            printTotalLOC(totalPhysicalLOC + "");
        }
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

    private void printTotalLOC(String totalPhysicalLOC) {
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
            0,
            Constants.JAVA_FILE_STATUS_ERROR
        );
    }

    private SourceFile getNoClassFile(String filepath) {
        Path file = Paths.get(filepath);

        return new SourceFile(
            file.getFileName().toString(),
            0,
            0,
            0,
            Constants.JAVA_FILE_STATUS_NO_CLASS
        );
    }

}
