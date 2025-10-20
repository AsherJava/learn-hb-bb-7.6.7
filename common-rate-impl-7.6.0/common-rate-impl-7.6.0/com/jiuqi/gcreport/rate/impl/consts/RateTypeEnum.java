/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.rate.impl.consts;

public enum RateTypeEnum {
    QC("01", "\u671f\u521d\u6c47\u7387"),
    NC("010", "\u5e74\u521d\u6c47\u7387"),
    HIS("011", "\u5386\u53f2\u6c47\u7387"),
    QM("02", "\u671f\u672b\u6c47\u7387"),
    PJ("03", "\u6708\u5e73\u5747\u6c47\u7387"),
    NOTCONV("NOTCONV", "\u4e0d\u6298\u7b97"),
    SNPJ("04", "\u4e0a\u5e74\u5e73\u5747\u6c47\u7387"),
    FORMULA("05", "\u516c\u5f0f\u6c47\u7387"),
    COPY("COPY", "\u590d\u5236"),
    CALC("CALC", "\u7b49\u5f0f\u8fd0\u7b97"),
    SEGMENT_QC_LJ("01_02", "\u671f\u521d\u6c47\u7387|\u7d2f\u8ba1\u5206\u6bb5\u6298\u7b97"),
    SEGMENT_QC_BN("01_03", "\u671f\u521d\u6c47\u7387|\u672c\u5e74\u5206\u6bb5\u6298\u7b97"),
    SEGMENT_PJ_LJ("03_02", "\u6708\u5e73\u5747\u6c47\u7387|\u7d2f\u8ba1\u5206\u6bb5\u6298\u7b97"),
    SEGMENT_PJ_BN("03_03", "\u6708\u5e73\u5747\u6c47\u7387|\u672c\u5e74\u5206\u6bb5\u6298\u7b97"),
    SEGMENT_FORMULA_LJ("05_02", "\u516c\u5f0f\u6c47\u7387|\u7d2f\u8ba1\u5206\u6bb5\u6298\u7b97"),
    SEGMENT_FORMULA_BN("05_03", "\u516c\u5f0f\u6c47\u7387|\u672c\u5e74\u5206\u6bb5\u6298\u7b97");

    private String code;
    private String title;

    private RateTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static RateTypeEnum getEnumByCode(String code) {
        for (RateTypeEnum rateTypeEnum : RateTypeEnum.values()) {
            if (!rateTypeEnum.getCode().equals(code)) continue;
            return rateTypeEnum;
        }
        return null;
    }
}

