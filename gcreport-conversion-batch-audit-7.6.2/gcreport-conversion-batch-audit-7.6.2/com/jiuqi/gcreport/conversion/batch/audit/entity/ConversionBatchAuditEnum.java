/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.conversion.batch.audit.entity;

public enum ConversionBatchAuditEnum {
    PREVIOUSPERIOD("PREVIOUSPERIOD", "\u4e0a\u4e00\u671f"),
    CURRENTPERIOD("CURRENTPERIOD", "\u5f53\u524d\u671f"),
    LATERPERIOD("LATERPERIOD", "\u4e0b\u4e00\u671f");

    private String code;
    private String title;

    public static ConversionBatchAuditEnum getEnumByCode(String code) {
        ConversionBatchAuditEnum[] enums;
        for (ConversionBatchAuditEnum gcReportNoticeEnumenum : enums = ConversionBatchAuditEnum.values()) {
            if (!gcReportNoticeEnumenum.getCode().equals(code)) continue;
            return gcReportNoticeEnumenum;
        }
        return null;
    }

    private ConversionBatchAuditEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

