/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.datascheme.web.param;

import io.swagger.annotations.ApiModelProperty;

public class DataCheckPM {
    @ApiModelProperty(value="\u6570\u636e\u65b9\u6848\u4e3b\u952e", allowEmptyValue=true)
    private String dataSchemeKey;
    @ApiModelProperty(value="\u6570\u636e\u5206\u7ec4\u4e3b\u952e", allowEmptyValue=true)
    private String dataGroupKey;
    @ApiModelProperty(value="\u6570\u636e\u660e\u7ec6\u8868\u4e3b\u952e", allowEmptyValue=true)
    private boolean dataTableKey;
    @ApiModelProperty(value="\u6570\u636e\u6307\u6807\u4e3b\u952e", allowEmptyValue=true)
    private String dataFieldKey;
    @ApiModelProperty(value="\u6807\u8bc6", allowEmptyValue=true)
    private String code;

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getDataGroupKey() {
        return this.dataGroupKey;
    }

    public void setDataGroupKey(String dataGroupKey) {
        this.dataGroupKey = dataGroupKey;
    }

    public boolean isDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(boolean dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String getDataFieldKey() {
        return this.dataFieldKey;
    }

    public void setDataFieldKey(String dataFieldKey) {
        this.dataFieldKey = dataFieldKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

