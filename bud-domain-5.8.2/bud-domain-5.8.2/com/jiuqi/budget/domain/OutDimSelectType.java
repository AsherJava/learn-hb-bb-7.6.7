/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

public enum OutDimSelectType {
    SELECT(0),
    ATTR_DEFAULT(1),
    ATTR_ALL(2);

    private int id;

    private OutDimSelectType(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}

