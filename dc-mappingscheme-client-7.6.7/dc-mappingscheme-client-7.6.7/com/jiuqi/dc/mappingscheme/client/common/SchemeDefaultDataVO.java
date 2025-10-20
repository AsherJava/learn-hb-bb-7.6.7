/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.client.common;

public class SchemeDefaultDataVO {
    private String customConfig;
    private String schemeDefaultDataVOList;
    private String defaultDataByPluginType;

    public SchemeDefaultDataVO() {
    }

    public SchemeDefaultDataVO(String schemeDefaultDataVOList, String defaultDataByPluginType) {
        this.schemeDefaultDataVOList = schemeDefaultDataVOList;
        this.defaultDataByPluginType = defaultDataByPluginType;
    }

    public SchemeDefaultDataVO(String customConfig, String schemeDefaultDataVOList, String defaultDataByPluginType) {
        this.customConfig = customConfig;
        this.schemeDefaultDataVOList = schemeDefaultDataVOList;
        this.defaultDataByPluginType = defaultDataByPluginType;
    }

    public String getCustomConfig() {
        return this.customConfig;
    }

    public void setCustomConfig(String customConfig) {
        this.customConfig = customConfig;
    }

    public String getSchemeDefaultDataVOList() {
        return this.schemeDefaultDataVOList;
    }

    public void setSchemeDefaultDataVOList(String schemeDefaultDataVOList) {
        this.schemeDefaultDataVOList = schemeDefaultDataVOList;
    }

    public String getDefaultDataByPluginType() {
        return this.defaultDataByPluginType;
    }

    public void setDefaultDataByPluginType(String defaultDataByPluginType) {
        this.defaultDataByPluginType = defaultDataByPluginType;
    }
}

