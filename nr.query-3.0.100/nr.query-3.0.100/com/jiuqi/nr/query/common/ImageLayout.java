/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.common;

public enum ImageLayout {
    STRETCH("0"),
    NONE("1"),
    ZOOM("2"),
    TILE("3"),
    CENTER("4");

    private String name;

    private ImageLayout(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name();
    }
}

