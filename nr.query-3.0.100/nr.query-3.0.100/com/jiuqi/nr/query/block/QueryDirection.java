/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.block;

public enum QueryDirection {
    COLDIRECTION("colDirection"),
    ROWDIRECTION("rowDirection");

    private String value;

    private QueryDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

