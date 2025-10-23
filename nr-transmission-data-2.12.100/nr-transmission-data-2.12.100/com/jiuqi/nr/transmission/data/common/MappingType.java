/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.common;

public enum MappingType {
    ANALYSIS_MAPPING(-1, "\u9884\u89e3\u6790"),
    NO_MAPPING(0, "\u4e0d\u6620\u5c04"),
    EXPORT_MAPPING(1, "\u5bfc\u51fa\u65f6\u6620\u5c04"),
    IMPORT_MAPPING(2, "\u5bfc\u5165\u65f6\u6620\u5c04");

    private int value;
    private String title;

    private MappingType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

