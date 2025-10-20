/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.enums;

public enum GcCarryOverInvestTypeEnum {
    INVESTMENT("INVESTMENT"),
    PUBLIC_ADJUSTMENT("PUBLIC_ADJUSTMENT");

    private String code;

    private GcCarryOverInvestTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

