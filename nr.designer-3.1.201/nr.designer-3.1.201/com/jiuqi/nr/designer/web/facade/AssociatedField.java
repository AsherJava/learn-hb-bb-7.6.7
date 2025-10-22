/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssociatedField {
    @JsonProperty(value="FieldName")
    private String fieldName;
    @JsonProperty(value="FieldTitle")
    private String fieldTitle;
    @JsonProperty(value="IsLevels")
    private boolean isLevels;

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public boolean getIsLevels() {
        return this.isLevels;
    }

    public void setIsLevels(boolean isLevels) {
        this.isLevels = isLevels;
    }
}

