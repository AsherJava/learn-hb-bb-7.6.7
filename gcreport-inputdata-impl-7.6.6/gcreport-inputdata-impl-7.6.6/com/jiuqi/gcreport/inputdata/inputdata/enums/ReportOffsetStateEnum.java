/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.inputdata.enums;

public enum ReportOffsetStateEnum {
    NOTOFFSET("0"),
    OFFSET("1");

    private String value;

    private ReportOffsetStateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

