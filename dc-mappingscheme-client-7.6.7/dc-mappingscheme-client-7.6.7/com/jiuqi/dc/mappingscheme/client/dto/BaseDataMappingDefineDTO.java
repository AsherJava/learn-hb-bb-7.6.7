/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BaseDataMappingDefineDTO
extends DataMappingDefineDTO {
    private String ruleType;
    private String autoMatchDim;
    private String advancedSql;
    private String isolationStrategy;

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
}

