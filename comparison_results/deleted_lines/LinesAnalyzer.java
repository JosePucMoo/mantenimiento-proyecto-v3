package com.mantenimiento.morado.code.counter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.mantenimiento.morado.util.FileHelper;
import com.mantenimiento.morado.code.model.LineTag;
public abstract class LinesAnalyzer {
protected final List<String> oldLinesCleaned;
protected final List<String> newLinesCleaned;
private final List<Integer> positionsToMark;
public LinesAnalyzer(List<String> oldLines, List<String> newLines) {
this.oldLinesCleaned = cleanLines(oldLines);
this.newLinesCleaned = cleanLines(newLines);
this.positionsToMark = detectPositionsToMark();
}
private List<String> cleanLines(List<String> lines) {
return lines.stream()
.map(String::trim)
.filter(l -> !l.isEmpty())
.filter(l -> !l.startsWith("//"))
.filter(l -> !l.startsWith("/*") && !l.startsWith("*") && !l.endsWith("*/"))
.collect(Collectors.toList());
}
protected abstract List<Integer> detectPositionsToMark();
protected List<Integer> getPositionsToMark() {
return new ArrayList<>(positionsToMark);
}
protected int getPositionsToMarkCount() {
return positionsToMark.size();
}
protected void markAndWriteLines(String outputFileName, LineTag tag) {
for (int idx : positionsToMark) {
String original = oldLinesCleaned.get(idx);
oldLinesCleaned.set(idx, original + tag.getTag());
}
FileHelper.writeLinesByTag(
oldLinesCleaned,
outputFileName,
tag.name().toLowerCase()
);
}
}
