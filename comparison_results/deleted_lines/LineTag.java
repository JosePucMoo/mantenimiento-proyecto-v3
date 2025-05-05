package com.mantenimiento.morado.code.model;
public enum LineTag {
ADDED(" // línea agregada"),
DELETED(" // línea borrada"),
MODIFIED(" // línea modificada");
private final String tag;
LineTag(String tag) {
this.tag = tag;
}
public String getTag() {
return tag;
}
}
