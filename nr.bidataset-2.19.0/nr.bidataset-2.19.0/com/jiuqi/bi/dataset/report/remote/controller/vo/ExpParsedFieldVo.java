/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.FieldType
 */
package com.jiuqi.bi.dataset.report.remote.controller.vo;

import com.jiuqi.bi.dataset.model.field.FieldType;

public class ExpParsedFieldVo {
    private String key;
    private FieldType fieldType;
    private int datatype;
    private String errorMsg;

    public String getKey() {
        return this.key;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public int getDatatype() {
        return this.datatype;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public void setDatatype(int datatype) {
        this.datatype = datatype;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

