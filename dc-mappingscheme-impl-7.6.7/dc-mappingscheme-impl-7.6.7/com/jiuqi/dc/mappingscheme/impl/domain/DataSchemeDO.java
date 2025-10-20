/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.domain;

public class DataSchemeDO {
    private String id;
    private Long ver;
    private String code;
    private String name;
    private String pluginType;
    private String dataSourceCode;
    private String customConfig;
    private Integer stopFlag;
    private String sourceDataType;
    private String etlJobId;
    private String orgMappingType;
    private String ruleType;
    private String dataMapping;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
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

    public String getPluginType() {
        return this.pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }

    public String getDataSourceCode() {
        return this.dataSourceCode;
    }

    public void setDataSourceCode(String dataSourceCode) {
        this.dataSourceCode = dataSourceCode;
    }

    public Integer getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
    }

    public String getCustomConfig() {
        return this.customConfig;
    }

    public void setCustomConfig(String customConfig) {
        this.customConfig = customConfig;
    }

    public String getSourceDataType() {
        return this.sourceDataType;
    }

    public void setSourceDataType(String sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    public String getEtlJobId() {
        return this.etlJobId;
    }

    public void setEtlJobId(String etlJobId) {
        this.etlJobId = etlJobId;
    }

    public String getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getOrgMappingType() {
        return this.orgMappingType;
    }

    public void setOrgMappingType(String orgMappingType) {
        this.orgMappingType = orgMappingType;
    }

    public String getDataMapping() {
        return this.dataMapping;
    }

    public void setDataMapping(String dataMapping) {
        this.dataMapping = dataMapping;
    }

    public String toString() {
        return "DataSchemeDO [id=" + this.id + ", ver=" + this.ver + ", code=" + this.code + ", name=" + this.name + ", pluginType=" + this.pluginType + ", dataSourceCode=" + this.dataSourceCode + ", customConfig=" + this.customConfig + ", stopFlag=" + this.stopFlag + "]";
    }
}

