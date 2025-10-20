/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

import com.jiuqi.dc.base.common.enums.FieldType;
import java.util.ArrayList;
import java.util.List;

public class FieldBatchAttribute {
    private String fieldName;
    private FieldType fieldType;
    private List<Object> fieldValueList;

    public FieldBatchAttribute(String fieldName, FieldType fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.fieldValueList = new ArrayList<Object>();
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

    public List<Object> getFieldValueList() {
        return this.fieldValueList;
    }

    public void addFieldValueList(Object fieldValue) {
        this.fieldValueList.add(fieldValue);
    }
}

