/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.lease.leasebill.enums;

public enum InputTypeEnum {
    MANUAL("MANUAL", "\u624b\u52a8"),
    AUTO("AUTO", "\u81ea\u52a8");

    private String code;
    private String title;

    private InputTypeEnum(String code, String title) {
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
        for (InputTypeEnum value : InputTypeEnum.values()) {
            if (!value.getCode().equals(code)) continue;
            return value.getTitle();
        }
        throw new IllegalArgumentException("\u65e0\u6b64\u679a\u4e3e\u9879");
    }

    public static String codeOfTitle(String title) {
        for (InputTypeEnum value : InputTypeEnum.values()) {
            if (!value.getTitle().equals(title)) continue;
            return value.getCode();
        }
        throw new IllegalArgumentException("\u65e0\u6b64\u679a\u4e3e\u9879");
    }
}

