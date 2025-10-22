/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.splittable.web;

import io.swagger.annotations.ApiModelProperty;

public class SplitDataPM {
    @ApiModelProperty(value="\u62a5\u8868key")
    private String formKey;
    @ApiModelProperty(value="\u8bed\u8a00\u7c7b\u578b\uff0c\u6682\u65f6\u6ca1\u7528\uff0c\u4f20\u51651")
    private int languageType;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("\"formKey\":").append(this.formKey).append(", \"languageType\":").append(this.languageType).append('}');
        return sb.toString();
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public void setLanguageType(int languageType) {
        this.languageType = languageType;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public int getLanguageType() {
        return this.languageType;
    }
}

