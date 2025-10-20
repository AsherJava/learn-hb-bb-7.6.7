/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.common;

public enum FormatNumberEnum {
    NONE("none"),
    NOT("not"),
    YES("yes");

    private String name;

    private FormatNumberEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

