/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.bde.common.dto;

import io.swagger.annotations.ApiModelProperty;

public class DimensionValue {
    @ApiModelProperty(value="\u7ef4\u5ea6\u540d", name="name", required=true, example="DATETIME")
    private String name;
    @ApiModelProperty(value="\u7ef4\u5ea6\u503c", name="value", required=true, example="2018N0001")
    private String value;
    @ApiModelProperty(value="\u7ef4\u5ea6\u7c7b\u578b\uff08\u53ef\u4ee5\u4e0d\u4f20\uff09", name="type", required=false)
    private int type;

    public DimensionValue() {
    }

    public DimensionValue(DimensionValue dimensionValue) {
        this.name = dimensionValue.getName();
        this.value = dimensionValue.getValue();
        this.type = dimensionValue.getType();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

