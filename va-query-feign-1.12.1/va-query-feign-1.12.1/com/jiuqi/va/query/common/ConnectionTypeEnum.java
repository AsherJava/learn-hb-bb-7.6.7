/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.common;

public enum ConnectionTypeEnum {
    NORMAL("normal"),
    URLONLY("urlOnly");

    private String name;

    private ConnectionTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

