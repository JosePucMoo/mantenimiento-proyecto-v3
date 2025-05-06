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

    public AddedLinesAnalyzer(List<String> oldLines, List<String> newLines) {
        super(oldLines, newLines);
    }

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

    public List<Integer> getAddedLineIndices() {
        return getPositionsToMark();
    }

    public int getAddedLineCount() {
        return getPositionsToMarkCount();
    }

    public void markAndWriteAdded(String outputFileName) {
        markAndWriteLines(outputFileName, LineTag.ADDED);
    }
}
