/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.invest.investbillcarryover.enums;

import java.util.Objects;

public enum AccoutCarryOverModeEnum {
    END("01", "\u671f\u672b\u6570\u7ed3\u8f6c"),
    CHANGE("02", "\u53d8\u52a8\u6570\u7ed3\u8f6c"),
    CALCFORMULA("03", "\u8fd0\u7b97\u6570\u7ed3\u8f6c"),
    CHANGEZERO("04", "\u53d8\u52a8\u6570\u6e05\u96f6\u7ed3\u8f6c");

    private String code;
    private String title;

    private AccoutCarryOverModeEnum(String code, String title) {
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

    public static AccoutCarryOverModeEnum getEnumBycode(String code) {
        for (AccoutCarryOverModeEnum carryOverModeEnum : AccoutCarryOverModeEnum.values()) {
            if (!Objects.equals(carryOverModeEnum.getCode(), code)) continue;
            return carryOverModeEnum;
        }
        return null;
    }
}

