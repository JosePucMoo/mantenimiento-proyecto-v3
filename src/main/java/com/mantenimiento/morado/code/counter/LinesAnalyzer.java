package com.mantenimiento.morado.code.counter;

import java.util.ArrayList;
import java.util.List;
import com.mantenimiento.morado.util.FileHelper;
import com.mantenimiento.morado.util.LineSimilarityUtil;
import com.mantenimiento.morado.code.model.LineTag;

/**
 * Abstract class for analyzing differences between two versions of code lines.
 * It provides shared logic for cleaning lines, detecting relevant positions, 
 * and marking/writing modified lines.
 */
public abstract class LinesAnalyzer {
    
    protected final List<String> oldLinesCleaned;
    protected final List<String> newLinesCleaned;
    private final List<Integer> positionsToMark;

    /**
     * Constructs the analyzer by cleaning both versions of the input lines
     * and calculating the relevant positions to mark.
     *
     * @param oldLines list of lines from the old version
     * @param newLines list of lines from the new version
     */
    public LinesAnalyzer(List<String> oldLines, List<String> newLines) {
        this.oldLinesCleaned = cleanLines(oldLines);
        this.newLinesCleaned = cleanLines(newLines);
        this.positionsToMark = detectPositionsToMark();
    }

    /**
     * Cleans a list of code lines by trimming whitespace and removing comments and empty lines.
     *
     * @param lines list of raw code lines
     * @return cleaned list of lines
     */
    private List<String> cleanLines(List<String> lines) {
        List<String> cleaned = new ArrayList<>();
        boolean inBlockComment = false;

        for (String line : lines) {
            StringBuilder sb = new StringBuilder();
            boolean inString = false;
            int i = 0;

            while (i < line.length()) {
                if (inBlockComment) {
                    if (i + 1 < line.length() && line.charAt(i) == '*' && line.charAt(i + 1) == '/') {
                        inBlockComment = false;
                        i += 2;
                    } else {
                        i++;
                    }
                    continue;
                }

                char c = line.charAt(i);

                if (!inString && i + 1 < line.length()) {
                    char next = line.charAt(i + 1);

                    // Start of block comment
                    if (c == '/' && next == '*') {
                        inBlockComment = true;
                        i += 2;
                        continue;
                    }

                    // Start of line comment
                    if (c == '/' && next == '/') {
                        break; // ignore rest of line
                    }
                }

                // Toggle string state (handle escaped quotes too)
                if (c == '"' && (i == 0 || line.charAt(i - 1) != '\\')) {
                    inString = !inString;
                }

                sb.append(c);
                i++;
            }

            String result = sb.toString().trim();
            if (!result.isEmpty()) {
                cleaned.add(result);
            }
        }

        return cleaned;
    }

    

    /**
     * Abstract method to be implemented by subclasses to define how positions to mark are detected.
     *
     * @return list of indices in {@code oldLinesCleaned} to be marked
     */
    protected abstract List<Integer> detectPositionsToMark();

    /**
     * Returns a copy of the list of positions that were detected for marking.
     *
     * @return list of marked line indices
     */
    protected List<Integer> getPositionsToMark() {
        return new ArrayList<>(positionsToMark);
    }

    /**
     * Returns the number of positions that were detected for marking.
     *
     * @return count of marked line indices
     */
    protected int getPositionsToMarkCount() {
        return positionsToMark.size();
    }

    /**
     * Marks the lines at the detected positions by appending a tag,
     * and writes the result to a file.
     *
     * @param outputFileName name of the file to write to
     * @param tag            the {@link LineTag} to append to each marked line
     */
    protected void markAndWriteLines(String outputFileName, LineTag tag) {
        
        List<String> targetLines = 
            (tag == LineTag.DELETED) ? oldLinesCleaned : newLinesCleaned;

        for (int idx : positionsToMark) {
            String original = targetLines.get(idx);
            LineTag tagToApply = tag;
            if (tag == LineTag.ADDED && idx < oldLinesCleaned.size()) {
                boolean modified = LineSimilarityUtil.isModified(
                    oldLinesCleaned.get(idx), newLinesCleaned.get(idx)
                );
                tagToApply = modified ? LineTag.MODIFIED : LineTag.ADDED;
            }
            targetLines.set(idx, original + " " + tagToApply.getTag());
        }
        FileHelper.writeLinesByTag(
            targetLines, 
            outputFileName, 
            tag.name().toLowerCase()
        );
    }
}
