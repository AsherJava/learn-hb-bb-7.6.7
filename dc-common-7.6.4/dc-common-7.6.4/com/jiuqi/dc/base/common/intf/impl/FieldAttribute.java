/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.intf.impl;

import com.jiuqi.dc.base.common.enums.FieldType;

public class FieldAttribute {
    private String fieldName;
    private FieldType fieldType;
    private Object fieldValue;

    public FieldAttribute(String fieldName, FieldType fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public FieldAttribute(String fieldName, FieldType fieldType, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldType getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public Object getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }
}

