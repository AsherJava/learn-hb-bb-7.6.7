/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.vo.field;

public class OrgFiledComponentVO {
    private String componentType;
    private String label;
    private String code;
    private Boolean fold;
    private Boolean readOnly;
    private Boolean allowMultiple;
    private Boolean nullable;
    private Boolean enableVersion;
    private String refTableName;
    private String editable;
    private Double sortOrder;
    private String format;

    public OrgFiledComponentVO() {
    }

    public OrgFiledComponentVO(String componentType, String label, String code, boolean nullable) {
        this.componentType = componentType;
        this.label = label;
        this.code = code;
        this.nullable = nullable;
    }

    public Boolean getFold() {
        return this.fold;
    }

    public void setFold(Boolean fold) {
        this.fold = fold;
    }

    public String getComponentType() {
        return this.componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public Boolean getAllowMultiple() {
        return this.allowMultiple;
    }

    public void setAllowMultiple(Boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
    }

    public Boolean getNullable() {
        return this.nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public Boolean getEnableVersion() {
        return this.enableVersion;
    }

    public void setEnableVersion(Boolean enableVersion) {
        this.enableVersion = enableVersion;
    }

    public String getRefTableName() {
        return this.refTableName;
    }

    public void setRefTableName(String refTableName) {
        this.refTableName = refTableName;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Double sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getEditable() {
        return this.editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}

