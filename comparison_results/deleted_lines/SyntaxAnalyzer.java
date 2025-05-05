package com.mantenimiento.morado.code.syntax;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.util.Regex;
public class SyntaxAnalyzer {
public static boolean isJavaFileWellWritten(SourceFile sourceFile) {
try {
List<String> codeLines = sourceFile.getAllLinesFromFile();
if (hasMultiInstance(codeLines)) {
return false;
}
for (String line : codeLines) {
String trimmedLine = line.trim();
if (trimmedLine.startsWith("{")) {
return false;
}
if (trimmedLine.endsWith("}") && !trimmedLine.equals("}")) {
return false;
}
}
} catch (IOException ioException) {
System.out.println("Error while reading file: " + ioException.getMessage());
}
return true;
}
private static boolean hasMultiInstance(List<String> fileLines) {
Pattern pattern = Pattern.compile(Regex.MULTI_INSTANCE_REGEX);
boolean multiInstanceFound = fileLines.stream().anyMatch(line -> pattern.matcher(line).matches());
return multiInstanceFound;
}
public static boolean isClassJavaFile(SourceFile sourceFile) {
try {
List<String> codeLines = sourceFile.getAllLinesFromFile();
for (String line : codeLines) {
String trimmedLine = line.trim();
if (isClassLine(trimmedLine)) {
return true;
}
}
return false;
} catch (IOException ioException) {
System.out.println("Error while reading file: " + ioException.getMessage());
}
return true;
}
private static boolean isClassLine(String line) {
return line.matches(Regex.CLASS_REGEX);
}
}
