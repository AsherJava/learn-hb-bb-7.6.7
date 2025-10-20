/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import com.jiuqi.dc.mappingscheme.client.common.Columns;
import java.io.Serializable;
import java.util.List;

public class DimMappingDTO
implements Serializable {
    private static final long serialVersionUID = 1749556056878642390L;
    private String code;
    private String name;
    private String ruleType;
    private String autoMatchDim;
    private String odsFieldName;
    private String odsFieldTitle;
    private String isolationStrategy;
    private Boolean odsFieldFixedFlag;
    private Boolean isolationStrategyFixedFlag;
    private String fieldMappingType;
    private String advancedSql;
    private List<Columns> baseMapping;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getAutoMatchDim() {
        return this.autoMatchDim;
    }

    public void setAutoMatchDim(String autoMatchDim) {
        this.autoMatchDim = autoMatchDim;
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

    public String getIsolationStrategy() {
        return this.isolationStrategy;
    }

    public void setIsolationStrategy(String isolationStrategy) {
        this.isolationStrategy = isolationStrategy;
    }

    public Boolean getOdsFieldFixedFlag() {
        return this.odsFieldFixedFlag;
    }

    public void setOdsFieldFixedFlag(Boolean odsFieldFixedFlag) {
        this.odsFieldFixedFlag = odsFieldFixedFlag;
    }

    public Boolean getIsolationStrategyFixedFlag() {
        return this.isolationStrategyFixedFlag;
    }

    public void setIsolationStrategyFixedFlag(Boolean isolationStrategyFixedFlag) {
        this.isolationStrategyFixedFlag = isolationStrategyFixedFlag;
    }

    public String getFieldMappingType() {
        return this.fieldMappingType;
    }

    public void setFieldMappingType(String fieldMappingType) {
        this.fieldMappingType = fieldMappingType;
    }

    public String getAdvancedSql() {
        return this.advancedSql;
    }

    public void setAdvancedSql(String advancedSql) {
        this.advancedSql = advancedSql;
    }

    public List<Columns> getBaseMapping() {
        return this.baseMapping;
    }

    public void setBaseMapping(List<Columns> baseMapping) {
        this.baseMapping = baseMapping;
    }
}

