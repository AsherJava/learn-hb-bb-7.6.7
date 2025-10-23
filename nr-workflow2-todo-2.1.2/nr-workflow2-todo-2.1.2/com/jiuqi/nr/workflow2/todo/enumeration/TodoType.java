/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.enumeration;

public enum TodoType {
    COMPLETED("COMPLETED"),
    UNCOMPLETED("UNCOMPLETED");

    public final String title;

    private TodoType(String title) {
        this.title = title;
    }
}

