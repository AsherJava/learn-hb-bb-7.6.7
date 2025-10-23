/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.controller.dto;

public class ReverseModeCheckDTO {
    private String dataSchemeKey;
    private String tableKey;
    private String tableCode;
    private int fieldKind;
    private String fieldCode;
    private String fieldKey;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public int getFieldKind() {
        return this.fieldKind;
    }

    public void setFieldKind(int fieldKind) {
        this.fieldKind = fieldKind;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }
}

