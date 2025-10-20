/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.offset.enums;

public enum OffsetStateEnum {
    NOTOFFSET("0"),
    OFFSET("1");

    private String value;

    private OffsetStateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

