package com.mantenimiento.morado.analyzer;

import java.util.ArrayList;
import java.util.List;

import com.mantenimiento.morado.model.LineTag;

/**
 * Analyzer for detecting deleted lines by comparing an old version of code lines
 * with a new version. A line is considered deleted if:
 * <ul>
 *   <li>It exists in the old version but differs from the corresponding line in the new version.</li>
 *   <li>It exists in the old version but has no corresponding line in the new version (i.e., extra lines at the end).</li>
 * </ul>
 * 
 * @author Aaron Graniel
 * @author Fernando Joachin
 * @version 1.0.0
 */
public class DeletedLinesAnalyzer extends LinesAnalyzer {

    /**
     * Constructs the analyzer for detecting deleted lines.
     *
     * @param oldLines list of lines from the old version of the file
     * @param newLines list of lines from the new version of the file
     */
    public DeletedLinesAnalyzer(List<String> oldLines, List<String> newLines) {
        super(oldLines, newLines);
    }

    /**
     * Detects the positions in the old file that correspond to deleted lines.
     *
     * @return list of indices in the cleaned old file representing deleted lines
     */
    @Override
    protected List<Integer> detectPositionsToMark() {
        List<Integer> indices = new ArrayList<>();
        int commonLength = Math.min(oldLinesCleaned.size(), newLinesCleaned.size());

        for (int i = 0; i < commonLength; i++) {
            if (!oldLinesCleaned.get(i).equals(newLinesCleaned.get(i))) {
                indices.add(i);
            }
        }

        for (int i = commonLength; i < oldLinesCleaned.size(); i++) {
            indices.add(i);
        }
        return indices;
    }

    /**
     * Returns the indices of the deleted lines in the cleaned old file.
     * 
     * This method acts as a semantic alias for {@code getPositionsToMark()} from the parent class,
     * making the method purpose clearer in the context of deleted lines.
     *
     * @return list of deleted line indices
     */
    public List<Integer> getDeletedLineIndices() {
        return getPositionsToMark();
    }

    /**
     * Returns the total number of deleted lines detected.
     * 
     * This method acts as a semantic alias for {@code getPositionsToMarkCount()} from the parent class,
     * allowing the use of a more meaningful name in the context of deleted lines.
     *
     * @return count of deleted lines
     */
    public int getDeletedLineCount() {
        return getPositionsToMarkCount();
    }

    /**
     * Marks the deleted lines in the cleaned old file with the {@code DELETED} tag
     * and writes the result to the corresponding output file.
     * 
     * This method acts as a wrapper for the generic {@code markAndWriteLines(...)} method,
     * specifying the {@code LineTag.DELETED} as the tag to apply.
     *
     * @param outputFileName name of the file where marked lines will be written
     */
    public void markAndWriteDeleted(String outputFileName) {
        markAndWriteLines(outputFileName, LineTag.DELETED);
    }
}
