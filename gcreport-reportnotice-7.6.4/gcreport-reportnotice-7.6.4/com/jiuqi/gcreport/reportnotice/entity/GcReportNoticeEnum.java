/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportnotice.entity;

public enum GcReportNoticeEnum {
    PREVIOUSPERIOD("PREVIOUSPERIOD", "\u4e0a\u4e00\u671f"),
    CURRENTPERIOD("CURRENTPERIOD", "\u5f53\u524d\u671f"),
    LATERPERIOD("LATERPERIOD", "\u4e0b\u4e00\u671f"),
    UNITREPORT("UNITREPORT", "\u6bcf\u4e2a\u5355\u4f4d\u4e0a\u62a5\u65f6"),
    UNITRETURN("UNITRETURN", "\u6bcf\u4e2a\u5355\u4f4d\u9000\u56de\u65f6"),
    UNITEXAMINE("UNITEXAMINE", "\u6bcf\u4e2a\u5355\u4f4d\u9001\u5ba1\u65f6"),
    UNITEXAMINERETURN("UNITEXAMINERETURN", "\u6bcf\u4e2a\u5355\u4f4d\u9000\u5ba1\u65f6"),
    UNITREGULARREPORT("UNITREGULARREPORT", "\u5b9a\u671f\u53d1\u9001\u6240\u6709\u4e0a\u62a5\u5355\u4f4d"),
    UNITREGULAREXAMINE("UNITREGULAREXAMINE", "\u5b9a\u671f\u53d1\u9001\u6240\u6709\u9001\u5ba1\u5355\u4f4d"),
    UNITALLCHOOSE("UNITALLCHOOSE", "\u5b9a\u671f\u53d1\u9001\u6240\u6709\u9009\u62e9\u5355\u4f4d");

    private String code;
    private String title;

    public static GcReportNoticeEnum getEnumByCode(String code) {
        GcReportNoticeEnum[] enums;
        for (GcReportNoticeEnum gcReportNoticeEnumenum : enums = GcReportNoticeEnum.values()) {
            if (!gcReportNoticeEnumenum.getCode().equals(code)) continue;
            return gcReportNoticeEnumenum;
        }
        return null;
    }

    private GcReportNoticeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

