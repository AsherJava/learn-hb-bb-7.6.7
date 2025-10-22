/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.system.check.common;

public enum SCCondition {
    BLOCK("block"),
    NONE("none");

    private String name;

    private SCCondition(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

