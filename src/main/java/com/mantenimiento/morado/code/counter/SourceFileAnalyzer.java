package com.mantenimiento.morado.code.counter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.mantenimiento.morado.code.model.JavaProject;
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
        List<JavaProject> projectstoAnalyze = new ArrayList<>();

        DirectoryScanner scanner = new DirectoryScanner(this.directoryPaths.get(0));
        JavaProject oldProject = scanner.scanProject();

        if (oldProject.getSourceFiles().isEmpty()) {
            throw new IllegalArgumentException("No Java files found in the first directory: " + this.directoryPaths.get(0));
        }

        scanner = new DirectoryScanner(this.directoryPaths.get(1));
        JavaProject newProject = scanner.scanProject();

        if (newProject.getSourceFiles().isEmpty()) {
            throw new IllegalArgumentException("No Java files found in the second directory: " + this.directoryPaths.get(1));
        }

        projectstoAnalyze.add(oldProject);
        projectstoAnalyze.add(newProject);
        VersionComparator.compareVersions(projectstoAnalyze);

        FileFormatter formatter = new FileFormatter();

        for (JavaProject currentProject : projectstoAnalyze) {
            printHeader();
            analyzeProject(currentProject);

            List<SourceFile> files = currentProject.getSourceFiles();
            for (int i = 0; i < files.size(); i++) {
                SourceFile oldFile = (i < projectstoAnalyze.get(0).getSourceFiles().size())
                    ? projectstoAnalyze.get(0).getSourceFiles().get(i)
                    : null;
                SourceFile newFile = files.get(i);
                try {
                    formatter.formatFile(oldFile, newFile);
                } catch (IOException e) {
                    System.err.println("Error formatting file " + newFile.getFilename() + ": " + e.getMessage());
                }
            }
        }
    }

    /**
     * Analyzes all files in a Java project and updates their metrics.
     *
     * @param project the JavaProject to analyze
     * @throws NullPointerException if project is null
     */
    private void analyzeProject(JavaProject project) {
        for (SourceFile file : project.getSourceFiles()) {
            SourceFile analyzedFile = analyzeFile(file);
            printDetails(analyzedFile, project.getProjectName());
        }

        int totalPhysicalLOC = project.getTotalPhysicalLOC();
        if (totalPhysicalLOC > 0) {
            printTotalProyectLOC(totalPhysicalLOC + "");
        }
    }

    /**
     * Analyzes an individual source file, calculating metrics and validating syntax.
     *
     * @param analyzeToFile the source file to analyze
     * @return a new SourceFile instance with updated metrics
     * @throws NullPointerException if file is null
     */
    private SourceFile analyzeFile(SourceFile analyzeToFile) {
        if (SyntaxAnalyzer.isJavaFileWellWritten(analyzeToFile)) {
            LOCCounter.countLOC(analyzeToFile);
            if (!SyntaxAnalyzer.isClassJavaFile(analyzeToFile)) {
                return getNoClassFile(analyzeToFile, analyzeToFile.getPhysicalLOC());
            }
            return analyzeToFile;
        } else {
            return getBadSourceFile(analyzeToFile);
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
            file.getFilename().replaceFirst("\\.java$", ""),
            file.getNumOfMethods(),
            file.getPhysicalLOC(),
            "",
            file.getStatus()
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
     * @param sourceFile the Java file that is considered not well written
     * @return a {@link SourceFile} instance with error status
     */
    private SourceFile getBadSourceFile(SourceFile sourceFile) {
        sourceFile.setPhysicalLOC(0);
        sourceFile.setNumOfMethods(0);
        sourceFile.setAddedLines(0);
        sourceFile.setDeletedLines(0);
        sourceFile.setStatus(Constants.JAVA_FILE_STATUS_ERROR);
        return sourceFile;
    }

    /**
     * Creates a SourceFile object for a Java file .
     * This gets relevant info of the class to print.
     * 
     * @param filepath The path to the Java file.
     * @return A SourceFile object with a status of "no class".
     */
    private SourceFile getNoClassFile(SourceFile sourceFile, int physicalLOC) {
        sourceFile.setPhysicalLOC(physicalLOC);
        sourceFile.setNumOfMethods(0);
        sourceFile.setAddedLines(0);
        sourceFile.setDeletedLines(0);
        sourceFile.setStatus(Constants.JAVA_FILE_STATUS_NO_CLASS);
        return sourceFile;
    }

}
