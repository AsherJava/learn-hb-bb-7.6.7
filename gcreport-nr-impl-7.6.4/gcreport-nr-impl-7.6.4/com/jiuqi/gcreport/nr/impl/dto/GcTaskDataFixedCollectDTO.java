/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.nr.impl.dto;

public class GcTaskDataFixedCollectDTO {
    private String fieldCode;
    private String fieldName;
    private String modelName;
    private String fieldKind;
    private Object fieldValue;
    private Double floatOrder;
    private String id;

    public Double getFloatOrder() {
        return this.floatOrder;
    }

    public void setFloatOrder(Double floatOrder) {
        this.floatOrder = floatOrder;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getFieldKind() {
        return this.fieldKind;
    }

    public void setFieldKind(String fieldKind) {
        this.fieldKind = fieldKind;
    }

    public Object getFieldValue() {
        return this.fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }
}

