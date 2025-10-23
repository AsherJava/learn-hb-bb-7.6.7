/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.enumeration;

public enum TodoNodeType {
    REQUEST_REJECT("REQUEST_REJECT"),
    URGE_REPORT("URGE_REPORT");

    public final String title;

    private TodoNodeType(String title) {
        this.title = title;
    }
}

