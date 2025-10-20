/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.vo;

import com.jiuqi.dc.mappingscheme.client.vo.BizMappingVO;

public class AdvancedMappingVO {
    private String code;
    private String name;
    private String ruleType;
    private String isolationStrategy;
    private String fieldMappingType;
    private String advancedSql;
    private BizMappingVO bizMapping;
    private String storageType;

    public AdvancedMappingVO() {
    }

    public AdvancedMappingVO(String code, String name) {
        this.code = code;
        this.name = name;
        this.bizMapping = new BizMappingVO();
    }

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

    public String getIsolationStrategy() {
        return this.isolationStrategy;
    }

    public void setIsolationStrategy(String isolationStrategy) {
        this.isolationStrategy = isolationStrategy;
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

    public BizMappingVO getBizMapping() {
        return this.bizMapping;
    }

    public void setBizMapping(BizMappingVO bizMapping) {
        this.bizMapping = bizMapping;
    }

    public String getStorageType() {
        return this.storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }
}

