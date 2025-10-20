/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common;

public enum FileTypeOptionEnum {
    EXCEL("EXCEL"),
    PDF("PDF"),
    JIO("JIO"),
    TXT("TXT"),
    OFD("OFD"),
    CSV("CSV");

    private final String title;

    private FileTypeOptionEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}

