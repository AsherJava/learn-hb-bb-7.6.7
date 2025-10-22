/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.invest.investbill.enums;

import java.util.Objects;

public enum ChangeTypeEnum {
    CONTROLPOWER_UNCHANGED("01", "\u63a7\u5236\u6743\u4e0d\u53d8"),
    SC_CHANGE("02", "\u540c\u63a7\u53d8\u52a8"),
    UNSC_ACQUISITION("03", "\u975e\u540c\u63a7\u6536\u8d2d"),
    UNSC_DISPOSE("04", "\u975e\u540c\u63a7\u5904\u7f6e");

    private String code;
    private String title;

    private ChangeTypeEnum(String code, String title) {
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

    public static ChangeTypeEnum getEnumBycode(String code) {
        for (ChangeTypeEnum changeTypeEnum : ChangeTypeEnum.values()) {
            if (!Objects.equals(changeTypeEnum.getCode(), code)) continue;
            return changeTypeEnum;
        }
        return null;
    }
}

