/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.inputdata.enums;

public enum InputDataCheckStateEnum {
    NOTCHECK("0", "\u672a\u5bf9\u8d26"),
    CHECK("1", "\u5df2\u5bf9\u8d26");

    private final String value;
    private final String title;

    private InputDataCheckStateEnum(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public static InputDataCheckStateEnum value(String checkSrcType) {
        for (InputDataCheckStateEnum checkSrcTypeEnum : InputDataCheckStateEnum.values()) {
            if (!checkSrcTypeEnum.getValue().equals(checkSrcType)) continue;
            return checkSrcTypeEnum;
        }
        return null;
    }

    public String getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }
}

