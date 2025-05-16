package com.mantenimiento.morado.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.constants.RegexConstants;

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
            if (currentLine.length() > LINE_LIMIT) {
                formattedLines.addAll(processLongLine(currentLine));
            } else {
                formattedLines.add(currentLine);
            }
        }

        FileHelper.writeFileInFormattedFolder(newFile.getFilename(), formattedLines);
    }

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

    private int findNextQuote(String line, int start) {
        int singleQuote = line.indexOf(SINGLE_QUOTE, start);
        int doubleQuote = line.indexOf(QUOTE, start);

        if (singleQuote == -1) return doubleQuote;
        if (doubleQuote == -1) return singleQuote;

        return Math.min(singleQuote, doubleQuote);
    }

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

    private List<String> handleMethodLine(String currentLine) {
        List<String> parts = separateLineByParenthesis(currentLine);
        if (parts.size() > 1 && parts.get(1).length() > LINE_LIMIT) {
            List<String> parameters = separateLineByCharacter(parts.get(1), COMMA);
            parts.remove(1);
            parts.addAll(1, parameters);
        }
        return parts;
    }

    private List<String> handleFlowControlLine(String currentLine) {
        List<String> parts = separateLineByParenthesis(currentLine);
        if (parts.size() > 1 && parts.get(1).length() > LINE_LIMIT) {
            List<String> conditions = separateLineByCharacter(parts.get(1), SPACE);
            parts.remove(1);
            parts.addAll(1, conditions);
        }
        return parts;
    }

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
