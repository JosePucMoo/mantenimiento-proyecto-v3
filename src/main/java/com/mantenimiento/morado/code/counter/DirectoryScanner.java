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

/**
 * The {@code DirectoryScanner} class is responsible for scanning a given directory and its
 * subdirectories to retrieve Java source files and subdirectories.
 *
 * @author Rubén Alvarado
 * @author Diana Vazquez
 * @author David Perez
 * @version 2.0.0
 */
public class DirectoryScanner {
    private final String directoryPath;

    /**
     * Constructs a new DirectoryScanner with the specified directory path
     *
     * @param directoryPath The path to the directory containing Java source files or subdirectories.
     */
    public DirectoryScanner(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    /**
     * Scans the directory and creates a JavaProject with all found Java files.
     *
     * @return A JavaProject containing all Java source files found in the directory and subdirectories.
     */
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

    /**
     * Creates a basic SourceFile instance for a given file path.
     * This is a placeholder until proper analysis is done in SourceFileAnalyzer.
     */
    private SourceFile createSourceFile(String filePath) {
        Path path = Paths.get(filePath);
        return new SourceFile(
            path.getFileName().toString(),
            path.toString()
        );
    }

    /**
     * Retrieves a list of Java source files (*.java) from the specified directory.
     * This method walks through the given directory, filters out non-file entries, and returns only the
     * files with a ".java" extension as a list of strings representing their absolute paths.
     *
     * @param subdirectory The path to the directory containing Java source files.
     * @return A list of strings containing the absolute paths of all Java files found in the directory.
     *         If an error occurs while accessing the directory, an empty list is returned.
     *
     */
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

    /**
     * Retrieves a list of all subdirectories within the specified directory, including nested subdirectories.
     * This method walks through the directory tree recursively and collects all directories found.
     *
     * @return A list of Paths representing all subdirectories in the given directory.
     *         If an error occurs while accessing the directory, an empty list is returned.
     */
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

    /**
     * Retrieves the name of the directory from the specified directory path.
     * This method checks if the given path exists and is a directory, then returns its name.
     *
     * @return The name of the directory as a string.
     *         If the path does not exist or is not a valid directory, it prints an error message and returns null.
     */
    public String getDirectoryName() {
        Path currentDir = Paths.get(this.directoryPath); // No es necesario capturar IOException aquí
        if (Files.exists(currentDir) && Files.isDirectory(currentDir)) {
            return currentDir.getFileName().toString(); // Devuelve el nombre del directorio
        } else {
            System.err.println("El directorio no existe o no es válido: " + this.directoryPath);
            return null; // Retorna null si no es un directorio válido
        }
    }

    /**
     * Retrieves a stream of file paths from the directory.
     * Only regular files (non-directories) are included.
     *
     * @return A {@code Stream<Path>} containing paths of all regular files found in the
     * directory.
     * @throws IOException If an I/O error occurs while accessing the file system
     */
    private Stream<Path> getFilePaths (Path directory) throws IOException {
        return Files.list(directory)
            .filter(Files::isRegularFile);
    }

    /**
     * Filters a stream of file paths to include only Java source files.
     * A Java source file is determined by a filename that ends with ".java".
     *
     * @param paths A {@code Stream<Path>} containing file paths to be filtered
     * @return A {@code List<String>} of absolute paths as strings forfiles ending with ".java".
     */
    private List<String> filterJavaFiles(Stream<Path> paths) {
        return paths
                .map(Path::toString)
                .filter(string -> string.endsWith(".java"))
                .collect(Collectors.toList());
    }

    /**
     * Checks if the specified path corresponds to an existing file.
     *
     * @param path The file path to check.
     * @return {@code true} if the path corresponds to an existing file, {@code false} otherwise.
     */
    public boolean isFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    /**
     * Checks if the specified path corresponds to an existing directory.
     *
     * @param path The directory path to check.
     * @return {@code true} if the path corresponds to an existing directory, {@code false} otherwise.
     */
    public boolean isDirectory(String path) {
        File directory = new File(path);
        return directory.exists() && directory.isDirectory();
    }
}
