/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.check.enums;

public enum CheckAmtAccessModeEnum {
    BIGGER(0, "\u53d6\u5927\u503c"),
    SMALLER(1, "\u53d6\u5c0f\u503c");

    private Integer code;
    private String title;

    private CheckAmtAccessModeEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static CheckAmtAccessModeEnum getEnumByCode(Integer checkAmtAccessModeCode) {
        for (CheckAmtAccessModeEnum checkAmtAccessMode : CheckAmtAccessModeEnum.values()) {
            if (!checkAmtAccessMode.getCode().equals(checkAmtAccessModeCode)) continue;
            return checkAmtAccessMode;
        }
        return null;
    }
}

