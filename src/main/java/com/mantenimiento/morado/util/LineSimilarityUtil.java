package com.mantenimiento.morado.util;

import org.apache.commons.text.similarity.LevenshteinDistance;

/**
 * Utility class for determining the similarity between two lines
 * and classifying them as a modification or a new line.
 * 
 * @author Aaron Graniel
 * @version 1.0.0
 */
public final class LineSimilarityUtil {
    /**
     * Minimum similarity threshold above which two lines are considered modified.
     */
    private static final double MIN_SIMILARITY = 0.70;

    /**
     * Determines if two lines should be classified as a modification (true)
     * or as an entirely new line (false) based on similarity threshold.
     *
     * @param oldLine the original line
     * @param newLine the updated line
     * @return true if similarity is above or equal to 0.70, false otherwise
     * @throws NullPointerException if oldLine or newLine is null
     */
    public static boolean isModified(String oldLine, String newLine) {
        
        if (oldLine == null || newLine == null) {
            throw new NullPointerException("Lines to compare must not be null");
        }
        
        double similarity = calculateSimilarity(oldLine, newLine);
        
        return similarity >= MIN_SIMILARITY;
    }

    /**
     * Calculates the similarity between two strings 
     * based on a normalized Levenshtein distance.
     *
     * @param oldLine the original line
     * @param newLine the updated line
     * @return similarity ratio between 0.0 and 1.0
     */
    public static double calculateSimilarity(String oldLine, String newLine) {
        
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();

        int distance = levenshteinDistance.apply(oldLine, newLine);
        int maxLen = Math.max(oldLine.length(), newLine.length());
        double normalizedDistance;
        
        if (maxLen == 0) {
            normalizedDistance = 1.0;
        } else {
            normalizedDistance = (double) distance / maxLen;
        }
        
        double similarity = 1.0 - normalizedDistance;
        
        return similarity;
    }
}

