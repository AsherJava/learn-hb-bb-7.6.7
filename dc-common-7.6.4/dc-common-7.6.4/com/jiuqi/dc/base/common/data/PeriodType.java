/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.data;

public enum PeriodType {
    PREVIOUSPERIOD("\u524d\u4e00\u671f"),
    CURRENTPERIOD("\u5f53\u524d\u671f"),
    SELECTPERIOD("\u6307\u5b9a\u671f");

    private String describe;

    private PeriodType(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return this.describe;
    }
}

