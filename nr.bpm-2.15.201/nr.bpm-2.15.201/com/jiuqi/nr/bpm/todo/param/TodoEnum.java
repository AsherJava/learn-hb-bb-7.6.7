/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.todo.param;

public enum TodoEnum {
    ACTIVITI("\u5de5\u4f5c\u6d41\u5f85\u529e", 0),
    EXTENDS("\u5176\u4ed6\u7c7b\u578b\u5f85\u529e", 1);

    private final String typeName;
    private final int value;

    private TodoEnum(String typeName, int value) {
        this.typeName = typeName;
        this.value = value;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public int getValue() {
        return this.value;
    }
}

