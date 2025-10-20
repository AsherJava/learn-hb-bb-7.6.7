/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.cache.intf.CacheEntity
 */
package com.jiuqi.dc.mappingscheme.client.dto;

import com.jiuqi.dc.base.common.cache.intf.CacheEntity;
import com.jiuqi.dc.mappingscheme.client.vo.DataMappingVO;
import com.jiuqi.dc.mappingscheme.client.vo.DataSchemeOptionVO;
import java.io.Serializable;
import java.util.List;

public class DataSchemeDTO
implements Serializable,
CacheEntity {
    private static final long serialVersionUID = 291375143714133017L;
    private String id;
    private Long ver;
    private String code;
    private String name;
    private String pluginType;
    private String dataSourceCode;
    private String customConfig;
    private Integer stopFlag;
    private String pluginTypeName;
    private String initSchemeData;
    private String saveType;
    private String sourceDataType;
    private String etlJobId;
    private String orgMappingType;
    private String ruleType;
    private List<DataSchemeOptionVO> options;
    private DataMappingVO dataMapping;

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

    public String getCustomConfig() {
        return this.customConfig;
    }

    public void setCustomConfig(String customConfig) {
        this.customConfig = customConfig;
    }

    public Integer getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
    }

    public String getPluginTypeName() {
        return this.pluginTypeName;
    }

    public void setPluginTypeName(String pluginTypeName) {
        this.pluginTypeName = pluginTypeName;
    }

    public String getInitSchemeData() {
        return this.initSchemeData;
    }

    public void setInitSchemeData(String initSchemeData) {
        this.initSchemeData = initSchemeData;
    }

    public String getSaveType() {
        return this.saveType;
    }

    public void setSaveType(String saveType) {
        this.saveType = saveType;
    }

    public String getCacheKey() {
        return this.getCode();
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

    public String getOrgMappingType() {
        return this.orgMappingType;
    }

    public void setOrgMappingType(String orgMappingType) {
        this.orgMappingType = orgMappingType;
    }

    public String getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public List<DataSchemeOptionVO> getOptions() {
        return this.options;
    }

    public void setOptions(List<DataSchemeOptionVO> options) {
        this.options = options;
    }

    public DataMappingVO getDataMapping() {
        return this.dataMapping;
    }

    public void setDataMapping(DataMappingVO dataMapping) {
        this.dataMapping = dataMapping;
    }
}

