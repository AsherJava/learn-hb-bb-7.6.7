/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.check.enums;

public enum ReconciliationModeEnum {
    BALANCE("BALANCE", "\u4f59\u989d\u5bf9\u8d26\u6a21\u5f0f"),
    ITEM("ITEM", "\u5206\u5f55\u5bf9\u8d26\u6a21\u5f0f");

    private String code;
    private String title;

    private ReconciliationModeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static ReconciliationModeEnum getEnumByCode(String code) {
        for (ReconciliationModeEnum reconciliationModeEnum : ReconciliationModeEnum.values()) {
            if (!reconciliationModeEnum.getCode().equals(code)) continue;
            return reconciliationModeEnum;
        }
        return null;
    }
}

