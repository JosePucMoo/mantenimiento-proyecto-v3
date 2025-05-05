package com.mantenimiento.morado.util;
public class Regex {
public static final String MULTI_INSTANCE_REGEX = "^\\s*\\w+\\s+\\w+\\s*=\\s*[^,;]+\\s*,\\s*\\w+.*;\\s*$";
public static final String CLASS_REGEX = "(public|private|protected)(\\s\\w+)*\\s+(class).*\\s*(//.*)?$";
}
