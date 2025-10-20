/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.init.enums;

public enum CarryOverModuleEnum {
    INVEST("invest", "\u6295\u8d44\u53f0\u8d26"),
    PRISUBACCOUNT("priSubAccount", "\u603b\u5206\u5305\u53f0\u8d26"),
    ADJUSTENTRY("adjustEntry", "\u62b5\u9500\u5206\u5f55");

    private final String code;
    private final String title;

    private CarryOverModuleEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public String toString() {
        return this.code;
    }
}

