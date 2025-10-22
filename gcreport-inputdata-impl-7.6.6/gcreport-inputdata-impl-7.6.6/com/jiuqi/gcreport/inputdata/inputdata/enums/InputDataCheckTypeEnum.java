/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.inputdata.enums;

public enum InputDataCheckTypeEnum {
    AUTO_ITEM("0", "\u81ea\u52a8"),
    MANUAL_ITEM("1", "\u624b\u5de5");

    private final String value;
    private final String title;

    private InputDataCheckTypeEnum(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public static InputDataCheckTypeEnum value(String checkSrcType) {
        for (InputDataCheckTypeEnum inputDataCheckTypeEnum : InputDataCheckTypeEnum.values()) {
            if (!inputDataCheckTypeEnum.getValue().equals(checkSrcType)) continue;
            return inputDataCheckTypeEnum;
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

