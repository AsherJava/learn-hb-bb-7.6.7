/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 */
package com.jiuqi.va.basedata.domain;

import com.jiuqi.va.domain.datamodel.DataModelColumn;

public class BaseDataExcleColumn
extends DataModelColumn {
    private static final long serialVersionUID = 1L;
    private Boolean checkval;
    private Boolean required;
    private Boolean unique;
    private String driveField;
    private Boolean uppercase;
    private Boolean multiple;

    public Boolean getUnique() {
        return this.unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getDriveField() {
        return this.driveField;
    }

    public void setDriveField(String driveField) {
        this.driveField = driveField;
    }

    public Boolean getUppercase() {
        return this.uppercase;
    }

    public void setUppercase(Boolean uppercase) {
        this.uppercase = uppercase;
    }

    public Boolean getCheckval() {
        return this.checkval;
    }

    public void setCheckval(Boolean checkval) {
        this.checkval = checkval;
    }

    public Boolean getMultiple() {
        return this.multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }
}

