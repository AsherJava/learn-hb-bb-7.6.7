/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.adjustvchr.impl.enums;

public enum DcAdjustVchrSysOptionEnum {
    DC_ADJUSTVOUCHER_MOUTH("Y", "\u6708\u5ea6\u8c03\u6574"),
    DC_ADJUSTVOUCHER_QUARTERLY("J", "\u5b63\u5ea6\u8c03\u6574"),
    DC_ADJUSTVOUCHER_HALFYEAR("H", "\u534a\u5e74\u5ea6\u8c03\u6574"),
    DC_ADJUSTVOUCHER_YEAR("N", "\u5e74\u5ea6\u8c03\u6574");

    private String periodTypeCode;
    private String title;

    private DcAdjustVchrSysOptionEnum(String periodTypeCode, String title) {
        this.periodTypeCode = periodTypeCode;
        this.title = title;
    }

    public static DcAdjustVchrSysOptionEnum getOptionIdByPeriodType(String periodTypeCode) {
        for (DcAdjustVchrSysOptionEnum option : DcAdjustVchrSysOptionEnum.values()) {
            if (!option.getPeriodTypeCode().equals(periodTypeCode)) continue;
            return option;
        }
        return null;
    }

    public String getPeriodTypeCode() {
        return this.periodTypeCode;
    }

    public String getTitle() {
        return this.title;
    }
}

