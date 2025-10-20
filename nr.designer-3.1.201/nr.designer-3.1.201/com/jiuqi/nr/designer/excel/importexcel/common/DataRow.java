/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.excel.importexcel.common;

public class DataRow {
    private String name;
    private String code;

    public DataRow(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

