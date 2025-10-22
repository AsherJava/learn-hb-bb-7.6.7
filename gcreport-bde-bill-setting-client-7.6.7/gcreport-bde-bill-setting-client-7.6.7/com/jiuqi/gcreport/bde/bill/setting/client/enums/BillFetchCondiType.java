/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.client.enums;

public enum BillFetchCondiType {
    FIX("FIX"),
    CUSTOM("CUSTOM");

    private final String code;

    private BillFetchCondiType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

