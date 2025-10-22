/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.common;

public enum SCSelectType {
    ALL("all"),
    CHOOSE("choose");

    private String name;

    private SCSelectType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

