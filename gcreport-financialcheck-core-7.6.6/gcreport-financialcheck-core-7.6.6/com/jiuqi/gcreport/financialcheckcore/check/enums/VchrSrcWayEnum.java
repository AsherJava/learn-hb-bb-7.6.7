/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.check.enums;

public enum VchrSrcWayEnum {
    BATCHINPUT("\u6279\u91cf\u5f55\u5165"),
    DATASYNC("\u6570\u636e\u540c\u6b65"),
    DATASYNC_CF("\u6570\u636e\u540c\u6b65-\u73b0\u6d41\u5206\u5f55"),
    UNCHECKCARRYOVER("\u5e74\u521d\u7ed3\u8f6c\u672a\u5bf9\u8d26"),
    DATACOLLECTION_CF("\u6570\u636e\u91c7\u96c6-\u73b0\u6d41\u5e95\u7a3f"),
    DATACOLLECTION_DIM("\u6570\u636e\u91c7\u96c6-\u591a\u7ef4\u5e95\u7a3f"),
    CHECKEDCARRYOVER("\u5e74\u521d\u7ed3\u8f6c\u5df2\u5bf9\u8d26"),
    INIT("\u521d\u59cb\u5316\u6570\u636e");

    private String des;

    private VchrSrcWayEnum(String des) {
        this.des = des;
    }

    public String getDes() {
        return this.des;
    }

    public static VchrSrcWayEnum fromName(String name) {
        for (VchrSrcWayEnum v : VchrSrcWayEnum.values()) {
            if (!v.name().equalsIgnoreCase(name)) continue;
            return v;
        }
        return null;
    }
}

