package com.mantenimiento.morado.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mantenimiento.morado.model.SourceFile;
import com.mantenimiento.morado.constants.RegexConstants;

/**
 * Utility class for formatting the content of source code files.
 * <p>
 * This class provides functionality to reformat source files
 * by processing long lines, handling comments, quoted strings,
 * and separating lines by specific characters or structures.
 * It ensures that lines do not exceed a predefined character limit.
 * </p>
 * 
 * @author David Muñoz
 * @author Andrea Torres
 * @version 1.0.0
 */
public class FileFormatter {
    private static final int LINE_LIMIT = 80;
    private static final char SPACE = ' ';
    private static final char PERIOD = '.';
    private static final char COMMA = ',';
    private static final char QUOTE = '"';
    private static final char SINGLE_QUOTE = '\'';
    private static final String LINE_COMMENT = "//";
    private static final String BLOCK_COMMENT_START = "/*";
    private static final String BLOCK_COMMENT_END = "*/";
    private static final String INDENTATION = "    ";

    /**
     * Formats a given source file by breaking long lines and applying line length limits.
     *
     * @param newFile The {@link SourceFile} to format.
     * @throws IOException If an I/O error occurs while writing the formatted file.
     * @throws IllegalArgumentException If the input file is null.
     */
    public void formatFile(SourceFile newFile) throws IOException {
        if (newFile == null) {
            throw new IllegalArgumentException("SourceFile cannot be null");
        }

        List<String> lines = newFile.getAllLinesFromFile();
        if (lines == null) {
            lines = Collections.emptyList();
        }

        List<String> formattedLines = new ArrayList<>();
        for (String currentLine : lines) {
            if (currentLine.trim().length() > LINE_LIMIT) {
                formattedLines.addAll(processLongLine(currentLine));
            } else {
                formattedLines.add(currentLine);
            }
        }

        FileHelper.writeFileInFormattedFolder(newFile.getFilename(), formattedLines);
    }

    /**
     * Processes a line that exceeds the line length limit.
     *
     * @param currentLine The line to process.
     * @return A list of formatted lines.
     */
    private List<String> processLongLine(String currentLine) {
        if (currentLine.contains(String.valueOf(QUOTE)) ||
            currentLine.contains(String.valueOf(SINGLE_QUOTE))) {
            return handleQuotedString(currentLine);
        } else if (currentLine.matches(RegexConstants.METHOD_REGEX) ||
                   currentLine.matches(RegexConstants.ABSTRACT_METHOD_REGEX)) {
            return handleMethodLine(currentLine);
        } else if (currentLine.matches(RegexConstants.FLOW_CONTROL_REGEX)) {
            return handleFlowControlLine(currentLine);
        } else if (currentLine.matches(RegexConstants.ONLY_SINGLE_LINE_COMMENT)) {
            return handleStandaloneComment(currentLine);
        } else if (currentLine.contains(LINE_COMMENT)) {
            return handleLineWithComment(currentLine);
        } else if (currentLine.matches(RegexConstants.IMPORT_OR_PACKAGE_REGEX)) {
            return separateLineByCharacter(currentLine, PERIOD);
        } else {
            return separateLineByCharacter(currentLine, SPACE);
        }
    }

    /**
     * Handles formatting of lines containing quoted strings.
     *
     * @param currentLine The line containing quoted strings.
     * @return A list of formatted lines.
     */
    private List<String> handleQuotedString(String currentLine) {
        List<String> parts = new ArrayList<>();
        int start = 0;

        while (start < currentLine.length()) {
            int quoteStart = findNextQuote(currentLine, start);
            if (quoteStart == -1) {
                addTrimmedPart(currentLine.substring(start), parts);
                break;
            }

            addTrimmedPart(currentLine.substring(start, quoteStart), parts);

            int quoteEnd = findMatchingQuoteEnd(currentLine, quoteStart);
            String quotedString = currentLine.substring(quoteStart, quoteEnd + 1);

            if (quotedString.length() > LINE_LIMIT) {
                parts.addAll(splitLongQuotedString(quotedString));
            } else {
                parts.add(quotedString);
            }
            start = quoteEnd + 1;
        }

        return parts;
    }

    /**
     * Adds a trimmed non-empty part to the result list, splitting if necessary.
     *
     * @param segment The string segment to process.
     * @param parts The list to which the processed segment is added.
     */
    private void addTrimmedPart(String segment, List<String> parts) {
        String trimmed = segment.trim();
        if (!trimmed.isEmpty()) {
            if (trimmed.length() > LINE_LIMIT) {
                parts.addAll(separateLineByCharacter(trimmed, SPACE));
            } else {
                parts.add(trimmed);
            }
        }
    }

    /**
     * Splits a quoted string into smaller parts respecting the line limit.
     *
     * @param quotedString The quoted string to split.
     * @return A list of formatted string chunks.
     */
    private List<String> splitLongQuotedString(String quotedString) {
        List<String> parts = new ArrayList<>();
        char quoteChar = quotedString.charAt(0);
        int maxChunkSize = LINE_LIMIT - 6;
        int start = 1;
        boolean isFirstPart = true;

        while (start < quotedString.length() - 1) {
            int end = Math.min(start + maxChunkSize, quotedString.length() - 1);
            if (end > start && quotedString.charAt(end - 1) == '\\') {
                end--;
            }

            String chunk = quotedString.substring(start, end);
            String formattedPart = (isFirstPart ? "" : INDENTATION) +
                                   quoteChar + chunk + quoteChar +
                                   (end < quotedString.length() - 1 ? " +" : "");

            parts.add(formattedPart);
            start = end;
            isFirstPart = false;
        }

        return parts;
    }

    /**
     * Finds the next quote character (single or double) from the given position.
     *
     * @param line The line to search.
     * @param start The starting index.
     * @return The index of the next quote, or -1 if not found.
     */
    private int findNextQuote(String line, int start) {
        int singleQuote = line.indexOf(SINGLE_QUOTE, start);
        int doubleQuote = line.indexOf(QUOTE, start);

        if (singleQuote == -1) return doubleQuote;
        if (doubleQuote == -1) return singleQuote;

        return Math.min(singleQuote, doubleQuote);
    }

    /**
     * Finds the matching quote character considering escaped quotes.
     *
     * @param line The line to search.
     * @param start The starting quote index.
     * @return The index of the matching quote.
     */
    private int findMatchingQuoteEnd(String line, int start) {
        char quoteChar = line.charAt(start);
        boolean escapeNext = false;

        for (int i = start + 1; i < line.length(); i++) {
            char c = line.charAt(i);
            if (escapeNext) {
                escapeNext = false;
                continue;
            }
            if (c == '\\') {
                escapeNext = true;
            } else if (c == quoteChar) {
                return i;
            }
        }

        return line.length() - 1;
    }

    /**
     * Handles formatting of method declaration lines.
     *
     * @param currentLine The method declaration line.
     * @return A list of formatted line segments.
     */
    private List<String> handleMethodLine(String currentLine) {
        List<String> parts = separateLineByParenthesis(currentLine);
        if (parts.size() > 1 && parts.get(1).length() > LINE_LIMIT) {
            List<String> parameters = separateLineByCharacter(parts.get(1), COMMA);
            parts.remove(1);
            parts.addAll(1, parameters);
        }
        return parts;
    }

    /**
     * Handles formatting of flow control lines (e.g., if, while).
     *
     * @param currentLine The flow control line.
     * @return A list of formatted line segments.
     */
    private List<String> handleFlowControlLine(String currentLine) {
        List<String> parts = separateLineByParenthesis(currentLine);
        if (parts.size() > 1 && parts.get(1).length() > LINE_LIMIT) {
            List<String> conditions = separateLineByCharacter(parts.get(1), SPACE);
            parts.remove(1);
            parts.addAll(1, conditions);
        }
        return parts;
    }

    /**
     * Handles lines that contain code followed by a comment.
     *
     * @param currentLine The line with comment.
     * @return A list containing the separated code and comment.
     */
    private List<String> handleLineWithComment(String currentLine) {
        int commentIndex = currentLine.indexOf(LINE_COMMENT);
        String codePart = currentLine.substring(0, commentIndex).trim();
        String commentPart = currentLine.substring(commentIndex);

        List<String> result = new ArrayList<>();
        if (!codePart.isEmpty()) {
            if (codePart.length() > LINE_LIMIT) {
                result.addAll(processLongLine(codePart));
            } else {
                result.add(codePart);
            }
        }
        result.add(commentPart);
        return result;
    }

    /**
     * Handles formatting of standalone single-line comments.
     *
     * @param currentLine The comment line.
     * @return A formatted block comment as a list of strings.
     */
    private List<String> handleStandaloneComment(String currentLine) {
        List<String> words = separateLineByCharacter(currentLine.substring(2), SPACE);
        List<String> formattedComment = new ArrayList<>();

        formattedComment.add(BLOCK_COMMENT_START + words.get(0));
        for (int i = 1; i < words.size(); i++) {
            formattedComment.add("* " + words.get(i));
        }
        int lastIndex = formattedComment.size() - 1;
        formattedComment.set(lastIndex, formattedComment.get(lastIndex) + BLOCK_COMMENT_END);

        return formattedComment;
    }

    /**
     * Separates a line into parts based on parenthesis for further formatting.
     *
     * @param currentLine The line to split.
     * @return A list with segments before, inside, and after parentheses.
     */
    private List<String> separateLineByParenthesis(String currentLine) {
        List<String> sentences = new ArrayList<>();
        int begin = currentLine.indexOf('(');
        int end = currentLine.lastIndexOf(')');

        if (begin == -1 || end == -1) {
            sentences.add(currentLine);
            return sentences;
        }

        sentences.add(currentLine.substring(0, begin + 1));
        sentences.add(currentLine.substring(begin + 1, end));
        sentences.add(currentLine.substring(end));
        return sentences;
    }

    /**
     * Splits a long line into smaller lines based on a given separator character.
     *
     * @param currentLine The line to split.
     * @param separator The character used to split the line.
     * @return A list of smaller segments.
     */
    private List<String> separateLineByCharacter(String currentLine, char separator) {
        List<String> sentences = new ArrayList<>();
        int start = 0;

        while (start < currentLine.length()) {
            int end = Math.min(start + LINE_LIMIT, currentLine.length());
            if (end < currentLine.length()) {
                int lastSep = currentLine.lastIndexOf(separator, end);
                if (lastSep > start) {
                    end = lastSep + 1;
                }
            }

            String part = currentLine.substring(start, end).trim();
            if (!part.isEmpty()) {
                sentences.add(part);
            }
            start = end;
        }
        return sentences;
    }
}
