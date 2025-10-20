/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.dto;

public class FieldMappingDTO {
    private String fieldName;
    private String fieldTitle;
    private String fieldMappingType;
    private String odsFieldName;
    private String odsFieldTitle;
    private String ruleType;
    private Integer fixedFlag;
    private Integer allowCreate;

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

    public String getFieldMappingType() {
        return this.fieldMappingType;
    }

    public void setFieldMappingType(String fieldMappingType) {
        this.fieldMappingType = fieldMappingType;
    }

    public String getOdsFieldName() {
        return this.odsFieldName;
    }

    public void setOdsFieldName(String odsFieldName) {
        this.odsFieldName = odsFieldName;
    }

    public String getOdsFieldTitle() {
        return this.odsFieldTitle;
    }

    public void setOdsFieldTitle(String odsFieldTitle) {
        this.odsFieldTitle = odsFieldTitle;
    }

    public String getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getFixedFlag() {
        return this.fixedFlag;
    }

    public void setFixedFlag(Integer fixedFlag) {
        this.fixedFlag = fixedFlag;
    }

    public Integer getAllowCreate() {
        return this.allowCreate;
    }

    public void setAllowCreate(Integer allowCreate) {
        this.allowCreate = allowCreate;
    }
}

