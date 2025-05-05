package com.mantenimiento.morado.code.counter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mantenimiento.morado.code.model.JavaProject;
import com.mantenimiento.morado.code.model.SourceFile;
public class VersionComparator {
public static void compareVersions(List<JavaProject> projectsToCompare){
JavaProject oldVersion = projectsToCompare.get(0);
JavaProject newVersion = projectsToCompare.get(1);
Map<String, SourceFile> oldVersionFiles = new HashMap<>();
for (SourceFile sourceFile : oldVersion.getSourceFiles()) {
oldVersionFiles.put(sourceFile.getFilename(), sourceFile);
}
for (SourceFile newFile : newVersion.getSourceFiles()) {
String newfilename = newFile.getFilename();
if (oldVersionFiles.containsKey(newfilename)) {
SourceFile oldFile = oldVersionFiles.get(newfilename);
try {
DeletedLinesAnalyzer deletedLinesAnlayzer = new DeletedLinesAnalyzer(
oldFile.getAllLinesFromFile(),
newFile.getAllLinesFromFile()
);
deletedLinesAnlayzer.markAndWriteDeleted(oldFile.getFilename());
oldFile.setDeletedLines(deletedLinesAnlayzer.getDeletedLineCount());
} catch (IOException ioException) {
System.out.println("Error while reading file: " + ioException.getMessage());
}
}
}
}
}
