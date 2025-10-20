/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.print;

public enum TableRowTypeEnum {
    fixedRow("fixedRow"),
    everyPage("everyPage"),
    excludeFirst("excludeFirst"),
    excludeEnd("excludeEnd"),
    onlyEndPage("onlyEndPage"),
    onlyFirstPage("onlyFirstPage"),
    floatRow("floatRow");

    private String name;

    private TableRowTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

