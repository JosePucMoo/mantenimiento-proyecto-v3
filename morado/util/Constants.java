package com.mantenimiento.morado.util;

/**
 * The {@code Constants} class defines constant values used throughout the application.
 * <p>
 * These constants represent different statuses for Java source files based on their syntax validation.
 * </p>
 *
 * @author Ruben Alvarado
 * @author Diana Vazquez
 * @version 2.0.0
 */
public class Constants {

    /**
     * Represents the status of a Java file that is considered well-written.
     */
    public static final String JAVA_FILE_STATUS_OK = "OK";

    /**
     * Represents the status of a Java file that contains syntax errors or does not follow the required conventions.
     */
    public static final String JAVA_FILE_STATUS_ERROR = "Bad file";

    /**
     * Represents the state of a Java file that does not contain a class
     */
    public static final String JAVA_FILE_STATUS_NO_CLASS = "No class";
}
