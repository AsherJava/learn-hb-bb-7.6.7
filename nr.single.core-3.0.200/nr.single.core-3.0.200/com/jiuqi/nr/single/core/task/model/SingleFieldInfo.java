/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task.model;

import com.jiuqi.nr.single.core.task.model.SingleDataType;

public class SingleFieldInfo {
    private String fieldCode;
    private String fieldTitle;
    private SingleDataType dataType;
    private int precision;
    private int decimal;
    private String mapFieldCode;

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public SingleDataType getDataType() {
        return this.dataType;
    }

    public void setDataType(SingleDataType dataType) {
        this.dataType = dataType;
    }

    public int getPrecision() {
        return this.precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public String getMapFieldCode() {
        return this.mapFieldCode;
    }

    public void setMapFieldCode(String mapFieldCode) {
        this.mapFieldCode = mapFieldCode;
    }
}

