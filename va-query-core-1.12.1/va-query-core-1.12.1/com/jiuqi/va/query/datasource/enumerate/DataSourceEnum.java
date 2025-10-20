/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.datasource.enumerate;

public enum DataSourceEnum {
    CURRENT("current");

    private String name;

    private DataSourceEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

