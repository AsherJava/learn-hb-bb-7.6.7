/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.common;

public enum BlockTitleAlign {
    CENTER("0"),
    LEFT("1");

    private String name;

    private BlockTitleAlign(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name();
    }
}

