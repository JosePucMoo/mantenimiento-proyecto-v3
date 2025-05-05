package com.mantenimiento.morado.code.counter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.mantenimiento.morado.code.model.JavaProject;
import com.mantenimiento.morado.code.model.SourceFile;
public class DirectoryScanner {
private final String directoryPath;
public DirectoryScanner(String directoryPath) {
this.directoryPath = directoryPath;
}
public JavaProject scanProject() {
String projectName = getDirectoryName();
List<SourceFile> sourceFiles = new ArrayList<>();
List<Path> subdirectories = getSubdirectories();
for (Path subdirectory : subdirectories) {
List<String> javaFilesPaths = getJavaFiles(subdirectory);
for (String filePath : javaFilesPaths) {
sourceFiles.add(createSourceFile(filePath));
}
}
JavaProject javaProject = new JavaProject(projectName, sourceFiles);
return javaProject;
}
private SourceFile createSourceFile(String filePath) {
Path path = Paths.get(filePath);
return new SourceFile(
path.getFileName().toString(),
path.toString()
);
}
public List<String> getJavaFiles(Path subdirectory) {
if (Files.isRegularFile(subdirectory)) {
return subdirectory.toString().endsWith(".java") ? List.of(subdirectory.toString()) : List.of();
}
try (Stream<Path> paths = getFilePaths(subdirectory)) {
return filterJavaFiles(paths);
} catch (IOException ioException) {
System.err.println("Error while trying to read directory path: " + ioException.getMessage());
}
return List.of();
}
public List<Path> getSubdirectories () {
try {
Stream<Path> paths = Files.walk(Paths.get(directoryPath));
return paths
.filter(Files::isDirectory)
.collect(Collectors.toList());
} catch (IOException ioException) {
System.err.println("Error while trying to read directory path: " + ioException.getMessage());
}
return List.of();
}
public String getDirectoryName() {
Path currentDir = Paths.get(this.directoryPath); // No es necesario capturar IOException aquí
if (Files.exists(currentDir) && Files.isDirectory(currentDir)) {
return currentDir.getFileName().toString(); // Devuelve el nombre del directorio
} else {
System.err.println("El directorio no existe o no es válido: " + this.directoryPath);
return null; // Retorna null si no es un directorio válido
}
}
private Stream<Path> getFilePaths (Path directory) throws IOException {
return Files.list(directory)
.filter(Files::isRegularFile);
}
private List<String> filterJavaFiles(Stream<Path> paths) {
return paths
.map(Path::toString)
.filter(string -> string.endsWith(".java"))
.collect(Collectors.toList());
}
public boolean isFile(String path) {
File file = new File(path);
return file.exists() && file.isFile();
}
public boolean isDirectory(String path) {
File directory = new File(path);
return directory.exists() && directory.isDirectory();
}
}
