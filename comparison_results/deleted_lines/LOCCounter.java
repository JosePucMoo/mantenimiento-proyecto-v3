package com.mantenimiento.morado.code.counter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.util.Constants;
public class LOCCounter {
private static int physicalLOC = 0;
private static int numOfMethods = 0;
public static void countLOC(SourceFile sourceFile) {
try {
List<String> codeLines = sourceFile.getAllLinesFromFile();
setPhysicalLOC(countPhysicalLOC(codeLines));
setNumOfMethods(countNumOfMethods(codeLines));
} catch (IOException ioException) {
System.err.println("Error while processing file: " + ioException.getMessage());
}
sourceFile.setPhysicalLOC(physicalLOC);
sourceFile.setNumOfMethods(numOfMethods);
sourceFile.setStatus(Constants.JAVA_FILE_STATUS_OK);
}
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
private static boolean endsBlockComment(String line) {
return line.endsWith("*/");
}
private static boolean startsBlockComment(String line) {
return line.startsWith("/*");
}
private static boolean isIgnorableLine(String line) {
return line.isEmpty() || line.startsWith("//") || line.startsWith("*");
}
private static boolean isMethodLine(String line) {
return line.matches("^(public|private|protected)\\s+[a-zA-Z\\s]*\\s*[\\w<>\\[\\],]*\\s*\\w+\\s*\\(.*\\)?\\s*.*\\{?\\s*(//.*)?$");
}
private static boolean isAbstractMethodLine(String line) {
return line.matches("^(public|private|protected)\\s(abstract)\\s+[\\w<>\\[\\],]+\\s+\\w+\\s*\\(.*\\)?\\s*(//.*)?");
}
private static void setPhysicalLOC(int _physicalLOC) {
physicalLOC = _physicalLOC;
}
private static void setNumOfMethods(int _numOfMethods) {
numOfMethods = _numOfMethods;
}
}
