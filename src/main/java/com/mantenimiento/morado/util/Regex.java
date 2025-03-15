package com.mantenimiento.morado.util;

/**
 * The {@code Regex} class contains regular expressions used for syntax validation in Java source files.
 * <p>
 * These patterns help identify specific coding conventions that may indicate improper formatting or style violations.
 * </p>
 *
 * @author Ruben Alvarado
 * @author Diana Vazquez
 * @version 2.0
 */
public class Regex {

    /**
     * Regular expression for detecting multi-instance variable declarations on a single line.
     * <p>
     * This pattern matches lines where multiple variables are declared and initialized on the same line,
     * separated by commas. For example:
     * </p>
     * <pre>
     * int a = 10, b = 20, c = 30;
     * String x = "hello", y = "world";
     * </pre>
     * <p>
     * These patterns are typically discouraged in certain coding standards for better readability.
     * </p>
     */
    public static final String MULTI_INSTANCE_REGEX = "^\\s*\\w+\\s+\\w+\\s*=\\s*[^,;]+\\s*,\\s*\\w+.*;\\s*$";
    public static final String CLASS_REGEX = "(public|private|protected)(\\s\\w+)*\\s+class\\s+\\w+(\\s+\\w+,?)*\\s*\\{\\s*(//.*)?$";
}
