/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import java.io.Serializable;
import java.util.Objects;

public class FieldMappingDefineDTO
implements Serializable {
    private static final long serialVersionUID = 9155739648243491654L;
    private String id;
    private String dataMappingId;
    private String tableName;
    private String dataSchemeCode;
    private String fieldName;
    private String fieldTitle;
    private String fieldMappingType;
    private String odsFieldName;
    private String ruleType;
    private Integer fixedFlag;
    private Long ordinal;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataMappingId() {
        return this.dataMappingId;
    }

    public void setDataMappingId(String dataMappingId) {
        this.dataMappingId = dataMappingId;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

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

    public Long getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(Long ordinal) {
        this.ordinal = ordinal;
    }

    public FieldMappingDefineDTO() {
    }

    public FieldMappingDefineDTO(String fieldName, String fieldTitle, String odsFieldName) {
        this.fieldName = fieldName;
        this.fieldTitle = fieldTitle;
        this.odsFieldName = odsFieldName;
    }

    public int hashCode() {
        return Objects.hash(this.fieldName);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FieldMappingDefineDTO obj = (FieldMappingDefineDTO)o;
        return Objects.equals(this.fieldName, obj.fieldName);
    }
}

