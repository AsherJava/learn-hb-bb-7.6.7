/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.enums;

public enum CalPeriodWayEnum {
    BEFORE("before", "\u4e0a\u4e00\u671f"),
    CURRENT("current", "\u5f53\u524d\u671f"),
    CUSTOM("custom", "\u6307\u5b9a\u671f");

    private String code;
    private String title;

    private CalPeriodWayEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static CalPeriodWayEnum getEnumByCode(String code) {
        for (CalPeriodWayEnum operateEnum : CalPeriodWayEnum.values()) {
            if (!operateEnum.getCode().equals(code)) continue;
            return operateEnum;
        }
        return null;
    }
}

