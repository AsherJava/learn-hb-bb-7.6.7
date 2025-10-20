/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.template.enumerate;

public enum AutoWidthTypeEnum {
    ADAPT("adapt"),
    REGULAR("regular");

    private String name;

    private AutoWidthTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

