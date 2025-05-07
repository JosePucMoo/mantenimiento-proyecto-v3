package com.mantenimiento.morado.code.counter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mantenimiento.morado.code.model.SourceFile;

public class FileFormatter {
    private final int lineLimit = 80;

    public void formatFile(SourceFile oldFile, SourceFile newFile) throws IOException{
        List<String> lines = (newFile != null) ? newFile.getAllLinesFromFile() : Collections.emptyList();

        for (int index = 0; index < lines.size(); index++) {
            if(lines.get(index).length()>lineLimit){
                List<String> sentences = new ArrayList<String>();
                int initialIndex = 0;
                int endIndex = lineLimit;
                
                while(endIndex<lines.get(index).length()) {
                    if(lines.get(index).charAt(endIndex)!=' '){
                        while (lines.get(index).charAt(endIndex)!=' ') {
                            endIndex--;
                        }
                    }
                    String line = lines.get(index).substring(initialIndex, endIndex-1);
                    sentences.add(line);
                    initialIndex = endIndex;
                    endIndex += lineLimit;
                }

                lines.remove(index);
                for (String sentence : sentences) {
                    lines.add(index, sentence);
                    index++;
                }
            }
        }
    }
}
