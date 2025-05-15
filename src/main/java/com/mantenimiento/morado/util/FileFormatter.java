package com.mantenimiento.morado.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mantenimiento.morado.code.model.SourceFile;

public class FileFormatter {
    private final int lineLimit = 80;

    public void formatFile(SourceFile newFile) throws IOException {
        List<String> lines = (newFile != null) ? newFile.getAllLinesFromFile() : Collections.emptyList();

        for (int index = 0; index < lines.size(); index++) {
            if (lines.get(index).length() > lineLimit) {
                List<String> sentences = new ArrayList<>();
                int initialIndex = 0;
                int endIndex = lineLimit;

                String currentLine = lines.get(index);

                while (endIndex < currentLine.length()) {
                    if (currentLine.charAt(endIndex) != ' ') {
                        while (endIndex > initialIndex && currentLine.charAt(endIndex) != ' ') {
                            endIndex--;
                        }
                    }
                    String line = currentLine.substring(initialIndex, endIndex);
                    sentences.add(line);
                    initialIndex = endIndex + 1;
                    endIndex = initialIndex + lineLimit;
                    if (endIndex > currentLine.length()) {
                        endIndex = currentLine.length();
                    }
                }

                // Add any leftover part
                if (initialIndex < currentLine.length()) {
                    sentences.add(currentLine.substring(initialIndex));
                }

                lines.remove(index);
                for (String sentence : sentences) {
                    lines.add(index, sentence);
                    index++;
                }
                index--;
            }
        }

        FileHelper.writeFileInFormattedFolder(newFile.getFilename(), lines);
    }
}
