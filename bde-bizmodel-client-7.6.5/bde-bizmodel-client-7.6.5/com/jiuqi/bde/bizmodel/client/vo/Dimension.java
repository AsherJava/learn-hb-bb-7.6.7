/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.client.vo;

public class Dimension {
    private String dimensionCode;
    private Boolean required;

    public Dimension() {
    }

    public Dimension(String dimensionCode, Boolean required) {
        this.dimensionCode = dimensionCode;
        this.required = required;
    }

    public String getDimensionCode() {
        return this.dimensionCode;
    }

    public void setDimensionCode(String dimensionCode) {
        this.dimensionCode = dimensionCode;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}

