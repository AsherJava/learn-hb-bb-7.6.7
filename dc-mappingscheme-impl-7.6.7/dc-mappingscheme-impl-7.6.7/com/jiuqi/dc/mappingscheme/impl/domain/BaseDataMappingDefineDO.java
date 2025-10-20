/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.domain;

import com.jiuqi.dc.mappingscheme.impl.domain.DataMappingDefineDO;

public class BaseDataMappingDefineDO
extends DataMappingDefineDO {
    private static final long serialVersionUID = 159955319950436482L;
    private String advancedSql;
    private String ruleType;
    private String autoMatchDim;

    public String getAdvancedSql() {
        return this.advancedSql;
    }

    public void setAdvancedSql(String advancedSql) {
        this.advancedSql = advancedSql;
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
}

