/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.enums;

public enum SameCtrlZbAttrEnum {
    NO_ZBATTR("noZbAttr", "\u65e0"),
    BEGIN_ZB("beginZb", "\u671f\u521d\u6570\u6307\u6807"),
    END_ZB("endZb", "\u671f\u672b\u6570\u6307\u6807"),
    SAME_PERIOD_LAST_YEAR_ZB("samePeriodLastYearZb", "\u4e0a\u5e74\u540c\u671f\u6570\u6307\u6807"),
    CUMULATIVE_THISY_EAR("cumulativeThisYear", "\u672c\u5e74\u7d2f\u8ba1\u6570\u6307\u6807");

    private String code;
    private String title;

    private SameCtrlZbAttrEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static String getTitleByCode(String code) {
        for (SameCtrlZbAttrEnum type : SameCtrlZbAttrEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type.getTitle();
        }
        return code;
    }
}

