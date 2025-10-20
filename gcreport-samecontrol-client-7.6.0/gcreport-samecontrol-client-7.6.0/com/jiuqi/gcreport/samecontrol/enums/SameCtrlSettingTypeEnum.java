/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.enums;

public enum SameCtrlSettingTypeEnum {
    MONTH("month", "\u671f\u521d\u6570"),
    YEAR("year", "\u4e0a\u5e74\u540c\u671f\u6570");

    private String code;
    private String title;

    private SameCtrlSettingTypeEnum(String code, String title) {
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
        for (SameCtrlSettingTypeEnum type : SameCtrlSettingTypeEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type.getTitle();
        }
        return code;
    }
}

