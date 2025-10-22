/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.conversionsystem.enums;

public enum RateTypeEnum {
    NOTCONV("00000000-0000-0000-0000-000000000000", "NOTCONV", "\u4e0d\u6298\u7b97"),
    NC("00000000-0000-0000-0000-000000000011", "011", "\u5e74\u521d\u6c47\u7387"),
    HIS("00000000-0000-0000-0000-000000000012", "012", "\u5386\u53f2\u6c47\u7387"),
    QC("00000000-0000-0000-0000-000000000001", "01", "\u671f\u521d\u6c47\u7387"),
    QM("00000000-0000-0000-0000-000000000002", "02", "\u671f\u672b\u6c47\u7387"),
    PJ("00000000-0000-0000-0000-000000000003", "03", "\u5e73\u5747\u6c47\u7387"),
    SNPJ("00000000-0000-0000-0000-000000000004", "04", "\u4e0a\u5e74\u5e73\u5747\u6c47\u7387"),
    FORMULA("00000000-0000-0000-0000-000000000005", "05", "\u516c\u5f0f\u6c47\u7387"),
    SEGMENT_QC_LJ("00000000-0000-0000-0000-000000000061", "061", "\u7d2f\u8ba1\u5206\u6bb5\u671f\u521d\u6c47\u7387"),
    SEGMENT_QC_BN("00000000-0000-0000-0000-000000000062", "062", "\u672c\u5e74\u5206\u6bb5\u671f\u521d\u6c47\u7387"),
    SEGMENT_PJ_LJ("00000000-0000-0000-0000-000000000063", "063", "\u7d2f\u8ba1\u5206\u6bb5\u5e73\u5747\u6c47\u7387"),
    SEGMENT_PJ_BN("00000000-0000-0000-0000-000000000064", "064", "\u672c\u5e74\u5206\u6bb5\u5e73\u5747\u6c47\u7387"),
    SEGMENT_FORMULA_LJ("00000000-0000-0000-0000-000000000065", "065", "\u7d2f\u8ba1\u5206\u6bb5\u516c\u5f0f\u6c47\u7387"),
    SEGMENT_FORMULA_BN("00000000-0000-0000-0000-000000000066", "066", "\u672c\u5e74\u5206\u6bb5\u516c\u5f0f\u6c47\u7387");

    private String id;
    private String code;
    private String title;

    private RateTypeEnum(String id, String code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String getId() {
        return this.id;
    }

    public static RateTypeEnum getEnumByCode(String code) {
        for (RateTypeEnum rateTypeEnum : RateTypeEnum.values()) {
            if (!rateTypeEnum.getCode().equals(code)) continue;
            return rateTypeEnum;
        }
        return null;
    }
}

