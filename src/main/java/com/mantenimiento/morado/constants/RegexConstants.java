package com.mantenimiento.morado.constants;

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
public class RegexConstants {

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
    public static final String MULTI_INSTANCE_REGEX = "^\\s*(?:final\\s+)?\\w+(?:<.*>)?\\s+\\w+\\s*=\\s*[^,;]+(?:\\s*,\\s*\\w+\\s*=\\s*[^,;]+)+\\s*;\\s*$";

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

    /**
     * Regular expression for detecting method declarations in Java files.
     * <p>
     * Matches methods with optional access modifiers and return types, including generics and arrays.
     * Also considers optional inline comments and optional opening curly braces.
     * </p>
     * <pre>
     * public void doSomething() {
     * private static List<String> getNames() throws IOException {
     * </pre>
     */
    public static final String METHOD_REGEX = "^(public|private|protected)\\s+[a-zA-Z\\s]*\\s*[\\w<>\\[\\],]*\\s*\\w+\\s*\\(.*\\)?\\s*.*\\{?\\s*(//.*)?$";

    /**
     * Regular expression for detecting abstract method declarations in Java files.
     * <p>
     * Matches lines that define abstract methods with optional access modifiers and return types.
     * </p>
     * <pre>
     * public abstract void process();
     * protected abstract List<String> computeValues();
     * </pre>
     */
    public static final String ABSTRACT_METHOD_REGEX = "^(public|private|protected)\\s+abstract\\s+[\\w<>\\[\\],]+\\s+\\w+\\s*\\(.*\\)?\\s*(//.*)?$";

    /**
     * Regular expression to match flow control statements (if, for, while, switch).
     * Example: "if (condition)", "for (int i = 0; i < 10; i++)".
     */
    public final static String FLOW_CONTROL_REGEX = "((if|for|while|switch)\\s*\\([^)]*\\))\\s*";

    public static final String ONLY_SINGLE_LINE_COMMENT = "^//.*";
    public final static String IMPORT_OR_PACKAGE_REGEX = "^\\s*(import|package)\\s+[a-zA-Z0-9_.*]+\\s*;$";
}
