/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.mobile.approval.vo;

import io.swagger.annotations.ApiModelProperty;

public class FieldDataInfo {
    @ApiModelProperty(value="\u6307\u6807\u4ee3\u7801", name="fieldCode")
    private String fieldCode;
    @ApiModelProperty(value="\u6307\u6807\u6807\u9898", name="fieldTitle")
    private String fieldTitle;
    @ApiModelProperty(value="\u6307\u6807\u7c7b\u578b", name="fieldType")
    private int fieldType;
    @ApiModelProperty(value="\u6570\u636e", name="data")
    private String data;

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

    public int getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(int fieldType) {
        this.fieldType = fieldType;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

