/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.variable;

public enum VariableType {
    CONST("NRConst", "\u5e38\u91cf"),
    FIELD("NRField", "\u6307\u6807"),
    FORMULA("NRFormula", "\u516c\u5f0f"),
    FORMDEFINE("NRGrid", "\u8868\u5355"),
    FORMSCHEMEDEFINE("NRFormScheme", "\u62a5\u8868\u65b9\u6848"),
    BICHART("BIChart", "BI\u56fe\u8868");

    private String code;
    private String title;

    private VariableType(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String toString() {
        return this.code + "_" + this.title;
    }
}

