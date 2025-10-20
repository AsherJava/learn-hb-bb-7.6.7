/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.print;

public enum BorderTypeFullEnum {
    NONE("0", 0),
    THIN("1", 1),
    MEDIUM("2", 2),
    DASHED("3", 1),
    DOTTED("4", 1),
    THICK("5", 3),
    DOUBLE("6", 3),
    HAIR("7", 1),
    MEDIUM_DASHED("8", 2),
    DASH_DOT("9", 1),
    MEDIUM_DASH_DOT("10", 2),
    DASH_DOT_DOT("11", 2),
    MEDIUM_DASH_DOT_DOT("12", 2),
    SLANTED_DASH_DOT("13", 2);

    private String name;
    private int width;

    private BorderTypeFullEnum(String name, int width) {
        this.width = width;
        this.name = name;
    }

    public static BorderTypeFullEnum getEnum(String name) {
        for (BorderTypeFullEnum v : BorderTypeFullEnum.values()) {
            if (!v.name.equalsIgnoreCase(name)) continue;
            return v;
        }
        return THIN;
    }

    public String getName() {
        return this.name;
    }

    public int getWidth() {
        return this.width;
    }
}

