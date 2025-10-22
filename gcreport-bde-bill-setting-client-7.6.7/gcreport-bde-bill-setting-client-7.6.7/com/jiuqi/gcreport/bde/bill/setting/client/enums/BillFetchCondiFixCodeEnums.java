/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.client.enums;

public enum BillFetchCondiFixCodeEnums {
    UNIT_CODE("UNIT_CODE"),
    START_DATE("START_DATE"),
    END_DATE("END_DATE"),
    CURRENCY("CURRENCY"),
    REPORT_PERIOD("REPORT_PERIOD"),
    GC_UNIT_TYPE("GC_UNIT_TYPE");

    private String code;

    private BillFetchCondiFixCodeEnums(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}

