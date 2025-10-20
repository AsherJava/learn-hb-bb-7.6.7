/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.define.match;

import com.jiuqi.bde.bizmodel.define.match.FilterRule;

public class BaseDataMatchCondi {
    private String pluginType;
    private FilterRule filterRule;
    private String assistDimCode;
    private String dataCode;

    public BaseDataMatchCondi() {
    }

    public BaseDataMatchCondi(String pluginType) {
        this.pluginType = pluginType;
    }

    public BaseDataMatchCondi(String pluginType, FilterRule filterRule, String assistDimCode, String dataCode) {
        this.pluginType = pluginType;
        this.filterRule = filterRule;
        this.assistDimCode = assistDimCode;
        this.dataCode = dataCode == null ? "" : dataCode.trim();
    }

    public String getPluginType() {
        return this.pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }

    public FilterRule getFilterRule() {
        return this.filterRule;
    }

    public BaseDataMatchCondi withFilterRule(FilterRule filterRule) {
        this.filterRule = filterRule;
        return this;
    }

    public String getAssistDimCode() {
        return this.assistDimCode;
    }

    public BaseDataMatchCondi withAssistCode(String assistCode) {
        this.assistDimCode = assistCode;
        return this;
    }

    public void setAssistDimCode(String assistDimCode) {
        this.assistDimCode = assistDimCode;
    }

    public void setFilterRule(FilterRule filterRule) {
        this.filterRule = filterRule;
    }

    public String getDataCode() {
        return this.dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode == null ? "" : dataCode.trim();
    }

    public BaseDataMatchCondi withDataCode(String dataCode) {
        this.dataCode = dataCode == null ? "" : dataCode.trim();
        return this;
    }
}

