/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.basedata.common;

public enum DummyObjType {
    ORG(0),
    SQLDEFINE(1),
    BASEDATA(2);

    private int code;

    private DummyObjType(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}

