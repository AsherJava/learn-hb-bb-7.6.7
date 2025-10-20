/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.common;

public enum FunctionEditorTypeEnum {
    INVEST("INVEST", "\u6295\u8d44"),
    INVENTORY("INVENTORY", "\u603b\u5206\u5305"),
    FAIRVALUE("FAIRVALUE", "\u516c\u5141\u4ef7\u503c"),
    FIXED_ASSETS("FIXED_ASSETS", "\u56fa\u5b9a\u8d44\u4ea7"),
    INPUTDATA("INPUTDATA", "\u5185\u90e8\u5f55\u5165\u8868"),
    RELATE_TRANSACTIONS("RELATE_TRANSACTIONS", "\u5173\u8054\u4ea4\u6613"),
    CONVERSION("CONVERSION", "\u5916\u5e01\u6298\u7b97"),
    OFFSETVCHRITEM("OFFSETVCHRITEM", "\u62b5\u9500\u5206\u5f55");

    private String value;
    private String label;

    private FunctionEditorTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

