/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.datascheme.api.DataField;

public class FieldItem {
    private String fieldCode;
    private String fieldKey;
    private String tableKey;
    private String fieldTitle;
    private int colIndex;

    public FieldItem(DataField fieldDefine, String tableKey, int colIndex) {
        this.fieldCode = fieldDefine.getCode();
        this.fieldKey = fieldDefine.getKey();
        this.fieldTitle = fieldDefine.getTitle();
        this.tableKey = tableKey;
        this.colIndex = colIndex;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getTableKey() {
        return this.tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public int getColIndex() {
        return this.colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }
}

