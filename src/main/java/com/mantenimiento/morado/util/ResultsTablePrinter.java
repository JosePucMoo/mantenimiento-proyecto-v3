package com.mantenimiento.morado.util;

import com.mantenimiento.morado.code.model.SourceFile;

/**
 * * Utility class for printing analysis results of Java source files in a formatted table.
 * <p>
 * This class is responsible for displaying the outcome of a static code analysis, including:
 * <ul>
 *   <li>File name and associated project version</li>
 *   <li>Number of methods and physical lines of code (LOC)</li>
 *   <li>Status indicating code quality or structural issues</li>
 * </ul>
 * </p>
 * 
 * 
 * @author José Puc
 * @version 1.0.0
 */
public class ResultsTablePrinter {

    /**
     * Template for formatting each column in a row.
     */
    private static final String COLUMN_FORMAT_TEMPLATE = 
    "%-18s %-30s %-18s %-18s %-18s %-10s%n";

    /**
     * Header for the program column.
     */
    private static final String HEADER_PROGRAM = "Program";
    
    /**
     * Header for the class column.
     */
    private static final String HEADER_CLASS = "Class";
    
    /**
     * Header for the methods count column.
     */
    private static final String HEADER_METHODS = "Number of methods";
    
    /**
     * Header for the physical lines of code (LOC) column.
     */
    private static final String HEADER_PHYSICAL_LOC = "Physical LOC";
    
    /**
     * Header for the total physical LOC column.
     */
    private static final String HEADER_TOTAL_LOC = "Total physical LOC";

    /**
     * Header for the Status column.
     */
    private static final String HEADER_STATUS = "Status";

    private static final String SEPARATOR = "---------------------------------------------------------------------------------------------------------------------------";

    /**
     * Prints the header for the LOC analysis results table.
     * <p>
     * The header includes the columns: "Program", "Logical LOC", "Physical LOC", and "Status".
     * </p>
     */
    public static void printHeader() {
        System.out.printf(COLUMN_FORMAT_TEMPLATE, HEADER_PROGRAM, HEADER_CLASS, HEADER_METHODS, HEADER_PHYSICAL_LOC, HEADER_TOTAL_LOC, HEADER_STATUS);
        System.out.println(SEPARATOR);
    }

    public static void printDetails(SourceFile file, String directoryName) {
        System.out.printf(
            COLUMN_FORMAT_TEMPLATE,
            directoryName,
            file.filename().replaceFirst("\\.java$", ""),
            file.numOfMethods(),
            file.physicalLOC(),
            "",
            file.status()
        );
    }

    /**
     * Prints the total physical lines of code (LOC) of a proyect in a formatted table.
     * 
     * @param totalPhysicalLOC The total number of physical LOC to print.
     */
    public static void printTotalProyectLOC(String totalPhysicalLOC) {
        System.out.println(SEPARATOR);
        System.out.printf(
            COLUMN_FORMAT_TEMPLATE,
            "Total Lines",
            "",
            "",
            "",
            totalPhysicalLOC,
            ""
        );
    }
}
