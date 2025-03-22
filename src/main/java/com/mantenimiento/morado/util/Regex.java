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

    /**
     * Regular expression for detecting class declarations in Java files.
     * <p>
     * This pattern matches lines where a class is declared, with optional access modifiers (public, private, protected),
     * and optional additional modifiers like static or abstract). The class declaration must include the keyword "class" followed by the class name. Comments at the end of the line are also allowed.
     * </p>
     * <pre>
     * public class MyClass {
     * private static class AnotherClass {
     * </pre>
     */
    public static final String CLASS_REGEX = "(public|private|protected)(\\s\\w+)*\\s+(class).*\\s*(//.*)?$";
}
