/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.multicriteria.enums;

public enum MulCriShowTypeEnum {
    ALL_TYPES("ALL_TYPES", "\u5168\u90e8"),
    MANUAL_ADJUST("MANUAL_ADJUST", "\u624b\u52a8\u8f6c\u6362\u6307\u6807"),
    HAVE_ADJUST_AMT("HAVE_ADJUST_AMT", "\u6709\u8c03\u6574\u91d1\u989d");

    private String code;
    private String title;

    private MulCriShowTypeEnum(String code, String title) {
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
        for (MulCriShowTypeEnum value : MulCriShowTypeEnum.values()) {
            if (!value.getCode().equals(code)) continue;
            return value.getTitle();
        }
        throw new IllegalArgumentException("\u65e0\u6b64\u679a\u4e3e\u9879");
    }

    public static String codeOfTitle(String title) {
        for (MulCriShowTypeEnum value : MulCriShowTypeEnum.values()) {
            if (!value.getTitle().equals(title)) continue;
            return value.getCode();
        }
        throw new IllegalArgumentException("\u65e0\u6b64\u679a\u4e3e\u9879");
    }
}

