/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.domain;

public enum RelationEnum {
    AND("AND", "\u4e14"),
    OR("OR", "\u6216");

    private final String name;
    private final String title;

    private RelationEnum(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }
}

