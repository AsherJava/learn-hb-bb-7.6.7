/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.params.output;

public class ExportFieldDefine {
    private String title;
    private String code;
    private int length;
    private int type;
    private int valueType;
    private String tableCode;

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public int getValueType() {
        return this.valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ExportFieldDefine() {
    }

    public ExportFieldDefine(String title, String code, int length, int type) {
        this.title = title;
        this.code = code;
        this.length = length;
        this.type = type;
    }
}

