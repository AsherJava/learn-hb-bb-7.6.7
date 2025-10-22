/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.common;

public enum BlockTitleRule {
    ALWAYS("0"),
    AUTOHIDE("1"),
    HIDE("2");

    private String name;

    private BlockTitleRule(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name();
    }
}

