package com.mantenimiento.morado.model;

/**
 * Enum representing the type of change applied to a line of code.
 * It is used to tag lines that have been added, deleted, or modified
 * during source code comparison.
 * 
 * @author Aaron Graniel
 * @version 1.0.0
 */
public enum LineTag {

    /**
     * Tag for a newly added line of code.
     */
    ADDED(" // línea nueva"),

    /**
     * Tag for a deleted line of code.
     */
    DELETED(" // línea borrada"),

    /**
     * Tag for a modified line of code.
     */
    MODIFIED(" // línea modificada");

    private final String tag;

    /**
     * Constructs a LineTag with the given textual tag to be appended to the line.
     *
     * @param tag the comment string that identifies the type of change
     */
    LineTag(String tag) {
        this.tag = tag;
    }

    /**
     * Returns the textual representation of the tag that can be appended to a line.
     *
     * @return the change tag string (e.g., " // línea agregada")
     */
    public String getTag() {
        return tag;
    }
}
