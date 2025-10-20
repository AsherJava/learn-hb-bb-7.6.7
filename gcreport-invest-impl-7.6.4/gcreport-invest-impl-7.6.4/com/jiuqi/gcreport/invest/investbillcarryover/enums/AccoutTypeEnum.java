/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.invest.investbillcarryover.enums;

import java.util.Objects;

public enum AccoutTypeEnum {
    INVESTMENT("INVESTMENT", "\u6295\u8d44\u53f0\u8d26"),
    FAIRVALUE("FAIRVALUE", "\u516c\u5141\u53f0\u8d26");

    private String code;
    private String title;

    private AccoutTypeEnum(String code, String title) {
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

    public static AccoutTypeEnum getEnumBycode(String code) {
        for (AccoutTypeEnum carryOverModeEnum : AccoutTypeEnum.values()) {
            if (!Objects.equals(carryOverModeEnum.getCode(), code)) continue;
            return carryOverModeEnum;
        }
        return null;
    }
}

