/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulaeditor.vo;

public class FieldData {
    private String order;
    private String key;
    private String code;
    private String title;
    private String type;
    private String tableCode;

    public FieldData() {
    }

    public FieldData(String key, String code, String title, String type, String tableCode) {
        this.key = key;
        this.code = code;
        this.title = title;
        this.type = type;
        this.tableCode = tableCode;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

