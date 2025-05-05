package com.mantenimiento.morado.code.model;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
public class SourceFile {
private final String filename;
private final String filePath;
private int physicalLOC;
private int numOfMethods;
private int addedLines;
private int deletedLines;
private String status;
public SourceFile(String filename, String filePath) {
this.filename     = filename;
this.filePath     = filePath;
this.physicalLOC  = 0;
this.numOfMethods = 0;
this.addedLines   = 0;
this.deletedLines = 0;
this.status       = "";
}
public List<String> getAllLinesFromFile() throws IOException {
return Files.readAllLines(Paths.get(this.filePath));
}
public String getFilename() {
return filename;
}
public String getFilePath() {
return filePath;
}
public int getPhysicalLOC() {
return physicalLOC;
}
public int getNumOfMethods() {
return numOfMethods;
}
public int getAddedLines() {
return addedLines;
}
public int getDeletedLines() {
return deletedLines;
}
public String getStatus() {
return status;
}
public void setPhysicalLOC(int physicalLOC) {
this.physicalLOC = physicalLOC;
}
public void setNumOfMethods(int numOfMethods) {
this.numOfMethods = numOfMethods;
}
public void setAddedLines(int addedLines) {
this.addedLines = addedLines;
}
public void setDeletedLines(int deletedLines) {
this.deletedLines = deletedLines;
}
public void setStatus(String status) {
this.status = status;
}
}
