/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.base.intf;

public class FloatDefineEO {
    private Integer fieldDefineOrder;
    private String fieldDefineId;
    private String fieldDefineType;
    private String fieldDefineName;

    public Integer getFieldDefineOrder() {
        return this.fieldDefineOrder;
    }

    public void setFieldDefineOrder(Integer fieldDefineOrder) {
        this.fieldDefineOrder = fieldDefineOrder;
    }

    public String getFieldDefineId() {
        return this.fieldDefineId;
    }

    public void setFieldDefineId(String fieldDefineId) {
        this.fieldDefineId = fieldDefineId;
    }

    public String getFieldDefineType() {
        return this.fieldDefineType;
    }

    public void setFieldDefineType(String fieldDefineType) {
        this.fieldDefineType = fieldDefineType;
    }

    public String getFieldDefineName() {
        return this.fieldDefineName;
    }

    public void setFieldDefineName(String fieldDefineName) {
        this.fieldDefineName = fieldDefineName;
    }

    public String toString() {
        return "FloatDefineEO{fieldDefineOrder=" + this.fieldDefineOrder + ", fieldDefineId='" + this.fieldDefineId + '\'' + ", fieldDefineType='" + this.fieldDefineType + '\'' + ", fieldDefineName='" + this.fieldDefineName + '\'' + '}';
    }
}

