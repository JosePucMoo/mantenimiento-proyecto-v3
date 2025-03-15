package com.mantenimiento.morado.code.counter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The {@code DirectoryScanner} class is responsible for scanning a given directory and its
 * subdirectories to retrieve Java source files.
 *
 * @author Rubén Alvarado
 * @author Diana Vazquez
 * @version 2.0.0
 */
public class DirectoryScanner {
    private final String directoryPath;

    /**
     * Constructs a new DirectoryScanner with the specified directory path
     *
     * @param directoryPath The path to the directory containing Java source files.
     */
    public DirectoryScanner(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    /**
     * Retrieves a list of Java source files (*.java) from the specified directory and its subdirectories.
     * This method walks through the given directory, filters out non-file entries, and returns only the
     * files with a ".java" extension as a list of strings representing their absolute paths.
     *
     * @return A list of strings containing the absolute paths of all Java files found in the directory.
     *         If an error occurs while accessing the directory, an empty list is returned.
     *
     */
    public List<String> getJavaFiles(Path subdirectory) {
        try (Stream<Path> paths = getFilePaths(subdirectory)) {
            return filterJavaFiles(paths);
        } catch (IOException ioException) {
            System.err.println("Error while trying to read directory path: " + ioException.getMessage());
        }
        return List.of();
    }

    public List<Path> getSubdirectories () {
        try {
            Stream<Path> paths = Files.list(Paths.get(directoryPath));
            return paths
            .filter(Files::isDirectory)
            .collect(Collectors.toList());
        } catch (IOException ioException) {
            System.err.println("Error while trying to read directory path: " + ioException.getMessage());
        }
        return List.of();
    }

    /**
     * Retrieves a stream of file paths from the directory and its subdirectories.
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
}
