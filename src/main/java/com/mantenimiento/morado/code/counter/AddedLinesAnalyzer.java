package com.mantenimiento.morado.code.counter;

import java.util.ArrayList;
import java.util.List;
import com.mantenimiento.morado.code.model.LineTag;

/**
 * Analyzer for detecting added lines by comparing an old version of code lines
 * with a new version. A line is considered added if:
 * <ul>
 *   <li>It exists in the new version but differs from the corresponding line in the old version.</li>
 *   <li>It exists in the new version but has no corresponding line in the old version (i.e., extra lines at the end).</li>
 * </ul>
 */
public class AddedLinesAnalyzer extends LinesAnalyzer {

    /**
     * Constructs the analyzer for detecting added lines.
     *
     * @param oldLines list of lines from the old version of the file
     * @param newLines list of lines from the new version of the file
     */
    public AddedLinesAnalyzer(List<String> oldLines, List<String> newLines) {
        super(oldLines, newLines);
    }

    /**
     * Detects the positions in the new file that correspond to added lines.
     *
     * @return list of indices in the cleaned new file representing added lines
     */
    @Override
    protected List<Integer> detectPositionsToMark() {
        List<Integer> indices = new ArrayList<>();
        int commonLength = Math.min(oldLinesCleaned.size(), newLinesCleaned.size());

        for (int i = 0; i < commonLength; i++) {
            if (!newLinesCleaned.get(i).equals(oldLinesCleaned.get(i))) {
                indices.add(i);
            }
        }

        for (int i = commonLength; i < newLinesCleaned.size(); i++) {
            indices.add(i);
        }

        return indices;
    }

    /**
     * Returns the indices of the added lines in the cleaned new file.
     *
     * This method acts as a semantic alias for {@code getPositionsToMark()} from the parent class,
     * making the method purpose clearer in the context of added lines.
     *
     * @return list of added line indices
     */
    public List<Integer> getAddedLineIndices() {
        return getPositionsToMark();
    }

    /**
     * Returns the total number of added lines detected.
     *
     * This method acts as a semantic alias for {@code getPositionsToMarkCount()} from the parent class,
     * allowing the use of a more meaningful name in the context of added lines.
     *
     * @return count of added lines
     */
    public int getAddedLineCount() {
        return getPositionsToMarkCount();
    }

    /**
     * Marks the added lines in the cleaned new file with the {@code ADDED} tag
     * and writes the result to the corresponding output file.
     *
     * This method acts as a wrapper for the generic {@code markAndWriteLines(...)} method,
     * specifying the {@code LineTag.ADDED} as the tag to apply.
     *
     * @param outputFileName name of the file where marked lines will be written
     */
    public void markAndWriteAdded(String outputFileName) {
        markAndWriteLines(outputFileName, LineTag.ADDED);
    }
}