/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.inputdata.enums;

public enum InputDataCheckTabEnum {
    AllDATA("AllDATA", "\u6240\u6709\u6570\u636e"),
    UNCHECKTAB("UNCHECKTAB", "\u672a\u5bf9\u8d26"),
    CHECKTAB("CHECKTAB", "\u5df2\u5bf9\u8d26");

    private final String tab;
    private final String title;

    private InputDataCheckTabEnum(String tab, String title) {
        this.tab = tab;
        this.title = title;
    }

    public static InputDataCheckTabEnum value(String tab) {
        for (InputDataCheckTabEnum inputDataCheckTabEnum : InputDataCheckTabEnum.values()) {
            if (!inputDataCheckTabEnum.getTab().equals(tab)) continue;
            return inputDataCheckTabEnum;
        }
        return null;
    }

    public String getTab() {
        return this.tab;
    }

    public String getTitle() {
        return this.title;
    }
}

