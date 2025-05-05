package com.mantenimiento.morado.code.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public final class JavaProject {
private String projectName;
private List<SourceFile> sourceFiles;
public JavaProject(String projectName, List<SourceFile> sourceFiles) {
this.projectName = Objects.requireNonNull(projectName, "Project name cannot be null");
this.sourceFiles = new ArrayList<>(Objects.requireNonNull(sourceFiles, "Source files list cannot be null"));
}
public String getProjectName() {
return this.projectName;
}
public void setProjectName(String projectName) {
this.projectName = Objects.requireNonNull(projectName, "Project name cannot be null");
}
public List<SourceFile> getSourceFiles() {
return new ArrayList<>(this.sourceFiles);
}
public void setSourceFiles(List<SourceFile> sourceFiles) {
this.sourceFiles = new ArrayList<>(Objects.requireNonNull(sourceFiles, "Source files list cannot be null"));
}
public int getTotalPhysicalLOC() {
return sourceFiles.stream()
.mapToInt(SourceFile::getPhysicalLOC)
.sum();
}
public int getTotalMethods() {
return sourceFiles.stream()
.mapToInt(SourceFile::getNumOfMethods)
.sum();
}
public int getTotalAddedLines() {
return sourceFiles.stream()
.mapToInt(SourceFile::getAddedLines)
.sum();
}
public int getTotalDeletedLines() {
return sourceFiles.stream()
.mapToInt(SourceFile::getDeletedLines)
.sum();
}
public void updateSourceFile(int index, SourceFile newFile) {
this.sourceFiles.set(index, Objects.requireNonNull(newFile, "Source file cannot be null"));
}
}
