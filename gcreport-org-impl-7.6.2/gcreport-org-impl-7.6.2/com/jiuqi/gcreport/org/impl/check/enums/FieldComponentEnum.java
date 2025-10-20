/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.check.enums;

public enum FieldComponentEnum {
    INPUT("INPUT", "input"),
    DATEPICKER("DATEPICKER", "DATEPICKER"),
    RADIOGROUP("RADIOGROUP", "RADIOGROUP"),
    ORGBASEDATA("ORGBASEDATA", "ORGBASEDATA"),
    BASEORG("BASEORG", "BASEORG"),
    BASEDATA("BASEDATA", "BASEDATA");

    private String code;
    private String title;

    private FieldComponentEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

