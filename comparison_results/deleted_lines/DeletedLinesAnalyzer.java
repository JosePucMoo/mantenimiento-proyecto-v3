package com.mantenimiento.morado.code.counter;
import java.util.ArrayList;
import java.util.List;
import com.mantenimiento.morado.code.model.LineTag;
public class DeletedLinesAnalyzer extends LinesAnalyzer {
public DeletedLinesAnalyzer(List<String> oldLines, List<String> newLines) {
super(oldLines, newLines);
}
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
public List<Integer> getDeletedLineIndices() {
return getPositionsToMark();
}
public int getDeletedLineCount() {
return getPositionsToMarkCount();
}
public void markAndWriteDeleted(String outputFileName) {
markAndWriteLines(outputFileName, LineTag.DELETED);
}
}
