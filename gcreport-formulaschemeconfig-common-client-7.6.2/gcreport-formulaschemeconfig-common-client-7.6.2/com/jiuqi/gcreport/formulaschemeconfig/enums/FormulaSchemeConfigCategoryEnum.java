/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.formulaschemeconfig.enums;

public enum FormulaSchemeConfigCategoryEnum {
    REPORT_FETCH("REPORT_FETCH", "\u62a5\u8868\u53d6\u6570"),
    BILL_FETCH("BILL_FETCH", "\u5355\u636e\u53d6\u6570");

    private final String code;
    private final String name;

    private FormulaSchemeConfigCategoryEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

