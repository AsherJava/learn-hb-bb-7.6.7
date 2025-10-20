/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.planpublish.common;

public enum WorkStatus {
    UNDO("undo"),
    DO("do");

    private String status;

    private WorkStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return this.status;
    }
}

