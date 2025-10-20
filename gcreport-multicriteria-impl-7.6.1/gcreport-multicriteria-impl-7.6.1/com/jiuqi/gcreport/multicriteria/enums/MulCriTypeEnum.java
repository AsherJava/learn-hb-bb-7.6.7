/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.multicriteria.enums;

public enum MulCriTypeEnum {
    MULTI_CRITERIA_MANUAL("manual", "\u624b\u52a8"),
    MULTI_CRITERIA_AUTO("auto", "\u81ea\u52a8");

    private String code;
    private String title;

    private MulCriTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static String titleOfCode(String code) {
        for (MulCriTypeEnum value : MulCriTypeEnum.values()) {
            if (!value.getCode().equals(code)) continue;
            return value.getTitle();
        }
        throw new IllegalArgumentException("\u65e0\u6b64\u679a\u4e3e\u9879");
    }

    public static String codeOfTitle(String title) {
        for (MulCriTypeEnum value : MulCriTypeEnum.values()) {
            if (!value.getTitle().equals(title)) continue;
            return value.getCode();
        }
        throw new IllegalArgumentException("\u65e0\u6b64\u679a\u4e3e\u9879");
    }
}

