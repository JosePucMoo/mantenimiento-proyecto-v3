package com.mantenimiento.morado.util;
import org.apache.commons.text.similarity.LevenshteinDistance;
public final class LineSimilarityUtil {
private static final double MIN_SIMILARITY = 0.70;
public static boolean isModified(String oldLine, String newLine) {
if (oldLine == null || newLine == null) {
throw new NullPointerException("Lines to compare must not be null");
}
double similarity = calculateSimilarity(oldLine, newLine);
return similarity >= MIN_SIMILARITY;
}
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
