package com.mantenimiento.morado.code.counter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.mantenimiento.morado.code.model.JavaProject;
import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.code.syntax.SyntaxAnalyzer;
import com.mantenimiento.morado.util.Constants;
public class SourceFileAnalyzer {
private final List<String> directoryPaths;
public SourceFileAnalyzer(List<String> directoryPaths) {
this.directoryPaths = directoryPaths;
}
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
for (JavaProject currentProject : projectstoAnalyze) {
printHeader();
analyzeProject(currentProject);
}
}
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
private void printHeader() {
System.out.printf("%-18s %-30s %-18s %-18s %-18s %-10s%n", "Program", "Class", "Number of methods", "Physical LOC", "Total physical LOC", "Status");
System.out.println("---------------------------------------------------------------------------------------------------------------------------");
}
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
private SourceFile getBadSourceFile(SourceFile sourceFile) {
sourceFile.setPhysicalLOC(0);
sourceFile.setNumOfMethods(0);
sourceFile.setAddedLines(0);
sourceFile.setDeletedLines(0);
sourceFile.setStatus(Constants.JAVA_FILE_STATUS_ERROR);
return sourceFile;
}
private SourceFile getNoClassFile(SourceFile sourceFile, int physicalLOC) {
sourceFile.setPhysicalLOC(physicalLOC);
sourceFile.setNumOfMethods(0);
sourceFile.setAddedLines(0);
sourceFile.setDeletedLines(0);
sourceFile.setStatus(Constants.JAVA_FILE_STATUS_NO_CLASS);
return sourceFile;
}
}
