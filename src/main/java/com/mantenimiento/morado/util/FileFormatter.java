package com.mantenimiento.morado.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mantenimiento.morado.code.model.SourceFile;
import com.mantenimiento.morado.constants.RegexConstants;

public class FileFormatter {
    private final int LINE_LIMIT = 80;
    private final char SPACE = ' ';
    private final char PERIOD = '.';
    private final char COMMA = ',';

    
    public void formatFile(SourceFile newFile) throws IOException {
        List<String> lines = (newFile != null) ? newFile.getAllLinesFromFile() : Collections.emptyList();

        for (int index = 0; index < lines.size(); index++) {
            String currentLine = lines.get(index);
            if (currentLine.length() > LINE_LIMIT) {
                List<String> sentences = new ArrayList<>();
                
                //métodos
                if(currentLine.matches(RegexConstants.METHOD_REGEX) || currentLine.matches(RegexConstants.ABSTRACT_METHOD_REGEX)){
                    sentences = separateLineByParenthesis(currentLine);
                    if(sentences.get(1).length() > LINE_LIMIT){
                        List<String> parameterList = separateLineByCharacter(currentLine, COMMA);
                        sentences.remove(1);
                        sentences.addAll(1, parameterList);
                    }
                }
                //estructuras de control
                else if(currentLine.matches(RegexConstants.FLOW_CONTROL_REGEX)){
                    sentences = separateLineByParenthesis(currentLine);
                    if(sentences.get(1).length() > LINE_LIMIT){
                        List<String> parameterList = separateLineByCharacter(currentLine, SPACE);
                        sentences.remove(1);
                        sentences.addAll(1, parameterList);
                    }
                }
                //si tiene comentario de línea -- separarlo
                else if(currentLine.matches(RegexConstants.SINGLE_LINE_COMMENT_IN_CODE_LINE)){
                    sentences = separateLineByLineComment(currentLine);
                    //volver a revisar la anterior
                    //volver a revisar comentario
                }
                //comentarios largos a comentarios de bloque
                else if(currentLine.matches(RegexConstants.ONLY_SINGLE_LINE_COMMENT)){
                    sentences = separateLineByCharacter(currentLine, SPACE);

                    sentences.set(0, "/*"+sentences.get(0).substring(2));
                    for (int i = 1; i < sentences.size()-1; i++) {
                        sentences.set(i, "*" + sentences.get(i));
                    }
                    sentences.set(sentences.size()-1, sentences.get(sentences.size()-1)+"*/");
                }
                //si import/package -- separar por .
                else if(currentLine.matches(RegexConstants.IMPORT_OR_PACKAGE_REGEX)){
                    sentences = separateLineByCharacter(currentLine, PERIOD);
                }
                //si hay "" -- separarlo con "+"
                else if(currentLine.matches(RegexConstants.QUOTED_STRING_REGEX)){
                    sentences = separateLineWithQuote(currentLine);
                }
                //a,a,a -- separar por coma
                //sino separar por espacios
                else{
                    sentences = separateLineByCharacter(currentLine, SPACE);
                }
                lines.remove(index);
                lines.addAll(index, sentences);
            }
        }

        FileHelper.writeFileInFormattedFolder(newFile.getFilename(), lines);
    }

    private List<String> separateLineByParenthesis (String currentLine) {
        List<String> sentences = new ArrayList<>();
        int beginParenthesis = currentLine.indexOf('(')+1;
        int endParenthesis = 0;
        for (int i = currentLine.length(); i > 0 ; i--) {
            if(currentLine.charAt(i) == ')'){
                endParenthesis = i;
            }
        }

        String line = currentLine.substring(0, beginParenthesis);
        sentences.add(line);
        
        line = currentLine.substring(beginParenthesis, endParenthesis);
        sentences.add(line);

        line = currentLine.substring(endParenthesis);
        sentences.add(line);

        return sentences;
    }

    private List<String> separateLineByCharacter (String currentLine, char separator) {
        List<String> sentences = new ArrayList<>();
        int initialIndex = 0;
        int endIndex = LINE_LIMIT;

        while (endIndex < currentLine.length()) {
            if (currentLine.charAt(endIndex) != separator) {
                while (endIndex > initialIndex && currentLine.charAt(endIndex) != separator) {
                    endIndex--;
                }
            }
            String line = currentLine.substring(initialIndex, endIndex);
            sentences.add(line);
            initialIndex = endIndex;
            endIndex = initialIndex + LINE_LIMIT;
            if (endIndex > currentLine.length()) {
                endIndex = currentLine.length();
            }
        }

        // Add any leftover part
        if (initialIndex < currentLine.length()) {
            sentences.add(currentLine.substring(initialIndex));
        }

        return sentences;
    }

    private List<String> separateLineByLineComment (String currentLine) {
        List<String> sentences = new ArrayList<>();
        int lineCommentStart = currentLine.length()-2;

        while (!(currentLine.charAt(lineCommentStart) == '/' && currentLine.charAt(lineCommentStart+1) == '/') 
                || lineCommentStart != 0) {
            lineCommentStart--;
        }

        String line = currentLine.substring(0, lineCommentStart);
        sentences.add(line);
        
        line = currentLine.substring(lineCommentStart);
        sentences.add(line);

        return sentences;
    }

    private List<String> separateLineWithQuote (String currentLine) {
        List<String> sentences = new ArrayList<>();

        /*int endPartOfLine = 0;
        char quoteMarc = '\"';

        while (currentLine.charAt(endPartOfLine) != '\"' && currentLine.charAt(endPartOfLine) !='\'') {
            endPartOfLine++;
        }

        if (endPartOfLine > LINE_LIMIT) {
            sentences.add(currentLine.substring(0, endPartOfLine));
        }
        else{
            quoteMarc = currentLine.charAt(endPartOfLine);
            int endIndex = LINE_LIMIT-1;
            if (currentLine.charAt(endIndex) != ' ') {
                while (endIndex > 0 && currentLine.charAt(endIndex) != ' ') {
                    endIndex--;
                }
            }
            sentences.add(currentLine.substring(0, endIndex)+quoteMarc);
            
            String partOfLine = quoteMarc + currentLine.substring(endIndex+1);
            endPartOfLine = 1;
            while (currentLine.charAt(endPartOfLine) != quoteMarc) {
                endPartOfLine++;
            }

        }*/

        return sentences;
    }
}
