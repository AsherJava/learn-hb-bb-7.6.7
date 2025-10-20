/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.common;

public class FieldDTO {
    private String name;
    private String title;
    private String tableName;
    private String ruleType;
    private String advancedSql;
    private String isolationStrategy;
    private Boolean odsFieldFixedFlag;
    private Boolean isolationStrategyFixedFlag;
    private String fieldMappingType;

    public FieldDTO() {
    }

    public FieldDTO(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getAdvancedSql() {
        return this.advancedSql;
    }

    public void setAdvancedSql(String advancedSql) {
        this.advancedSql = advancedSql;
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
}

