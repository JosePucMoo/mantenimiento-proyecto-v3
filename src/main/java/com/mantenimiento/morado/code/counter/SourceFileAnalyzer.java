package com.mantenimiento.morado.code.counter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.mantenimiento.morado.code.model.JavaProject;
import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.code.syntax.SyntaxAnalyzer;
import com.mantenimiento.morado.util.Constants;
import com.mantenimiento.morado.util.ResultsTablePrinter;

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
    private final List<String> directoryPaths;

    /**
     * Constructs a new {@code SourceFileAnalyzer} with the specified list of directory paths.
     *
     * @param directoryPaths The list of paths to directories containing Java source files,
     *                       typically ordered from oldest to newest versions.
     */
    public SourceFileAnalyzer(List<String> directoryPaths) {
        this.directoryPaths = directoryPaths;
    }

    /**
     * Analyzes all Java source files in the configured directories.
     *
     * The analysis process for each directory consists of:
     * - Scanning for Java source files.
     * - Validating the syntax of each source file.
     * - Calculating metrics such as lines of code (LOC), number of methods, and other statistics.
     * - Printing the formatted results.
     *
     * The output includes:
     * - Metrics for each analyzed file.
     * - Project-wide total metrics.
     * - Code quality status indicators.
     *
     * @return void
     */
    public void analyzePath() {
        for (String directoryPath : directoryPaths) {
            DirectoryScanner scanner = new DirectoryScanner(directoryPath);
            JavaProject project = scanner.scanProject();
            
            if (project.getSourceFiles().isEmpty()) {
                System.out.println("No Java files found in: " + directoryPath);
                continue;
            }
            ResultsTablePrinter.printHeader();
            analyzeProject(project);
        }
    }

    /**
     * Analyzes all files in a Java project and updates their metrics.
     *
     * @param project the JavaProject to analyze
     * @throws NullPointerException if project is null
     */
    private void analyzeProject(JavaProject project) {
        int index = 0;
        for (SourceFile file : project.getSourceFiles()) {
            SourceFile analyzedFile = analyzeFile(file);
            project.updateSourceFile(index, analyzedFile);
            index++;
            ResultsTablePrinter.printDetails(analyzedFile, project.getProjectName());
        }

        int totalPhysicalLOC = project.getTotalPhysicalLOC();
        if (totalPhysicalLOC > 0) {
            ResultsTablePrinter.printTotalProyectLOC(totalPhysicalLOC + "");
        }
    }

    /**
     * Analyzes an individual source file, calculating metrics and validating syntax.
     *
     * @param file the source file to analyze
     * @return a new SourceFile instance with updated metrics
     * @throws NullPointerException if file is null
     */
    private SourceFile analyzeFile(SourceFile file) {
        String filePath = Paths.get(directoryPaths.get(0), file.filename()).toString();
        if (SyntaxAnalyzer.isJavaFileWellWritten(filePath)) {
            SourceFile countedFile = LOCCounter.countLOC(filePath);
            if (!SyntaxAnalyzer.isClassJavaFile(filePath)) {
                return getNoClassFile(filePath, countedFile.physicalLOC());
            }
            return countedFile;
        } else {
            return getBadSourceFile(filePath);
        }
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
            0,
            0,
            Constants.JAVA_FILE_STATUS_NO_CLASS
        );
    }

}
