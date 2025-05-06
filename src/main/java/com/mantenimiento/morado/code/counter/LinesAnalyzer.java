package com.mantenimiento.morado.code.counter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.mantenimiento.morado.util.FileHelper;
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
        String joined = String.join("\n", lines);
        joined = joined.replaceAll("(?s)/\\*.*?\\*/", "");
        String[] split = joined.split("\\R");
    
        return Arrays.stream(split)
                     .filter(l -> !l.startsWith("//"))
                     .filter(l -> !l.trim().isEmpty())
                     .collect(Collectors.toList());
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
        List<String> currentLinesCleaned = 
            (tag.getTag() == LineTag.DELETED.getTag()) ? oldLinesCleaned : newLinesCleaned;
        for (int idx : positionsToMark) {
            String original = currentLinesCleaned.get(idx);
            currentLinesCleaned.set(idx, original + tag.getTag());
        }
        FileHelper.writeLinesByTag(
            currentLinesCleaned,
            outputFileName,
            tag.name().toLowerCase()
        );
    }
}
