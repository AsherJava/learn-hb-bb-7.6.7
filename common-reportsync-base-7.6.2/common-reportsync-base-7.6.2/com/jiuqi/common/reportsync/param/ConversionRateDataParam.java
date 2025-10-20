/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.reportsync.param;

public class ConversionRateDataParam {
    private String conversionSystemId;
    private String periodType;
    private String periodStr;
    private Integer periodOffset;

    public String getConversionSystemId() {
        return this.conversionSystemId;
    }

    public void setConversionSystemId(String conversionSystemId) {
        this.conversionSystemId = conversionSystemId;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public Integer getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(Integer periodOffset) {
        this.periodOffset = periodOffset;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }
}

