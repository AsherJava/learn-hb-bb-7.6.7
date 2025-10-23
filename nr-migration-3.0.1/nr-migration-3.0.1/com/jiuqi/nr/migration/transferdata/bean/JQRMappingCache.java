/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

import java.util.Map;
import org.springframework.util.StringUtils;

public class JQRMappingCache {
    private String tableNameMapping;
    private String verion;
    private String solutionCodeMapping;
    private Map<String, String> periodMappingMap;
    private Map<String, String> zbMappingMap;
    private Map<String, String> orgMappingMap;
    private Map<String, Map<String, String>> baseDataMappingMap;

    public String getTableNameMapping() {
        return this.tableNameMapping;
    }

    public void setTableNameMapping(String tableNameMapping) {
        this.tableNameMapping = tableNameMapping;
    }

    public String getVerion() {
        if (StringUtils.hasLength(this.verion)) {
            return this.verion;
        }
        return "5.7";
    }

    public void setVerion(String verion) {
        this.verion = verion;
    }

    public String getSolutionCodeMapping() {
        return this.solutionCodeMapping;
    }

    public void setSolutionCodeMapping(String solutionCodeMapping) {
        this.solutionCodeMapping = solutionCodeMapping;
    }

    public Map<String, String> getPeriodMappingMap() {
        return this.periodMappingMap;
    }

    public void setPeriodMappingMap(Map<String, String> periodMappingMap) {
        this.periodMappingMap = periodMappingMap;
    }

    public Map<String, String> getZbMappingMap() {
        return this.zbMappingMap;
    }

    public void setZbMappingMap(Map<String, String> zbMappingMap) {
        this.zbMappingMap = zbMappingMap;
    }

    public Map<String, String> getOrgMappingMap() {
        return this.orgMappingMap;
    }

    public void setOrgMappingMap(Map<String, String> orgMappingMap) {
        this.orgMappingMap = orgMappingMap;
    }

    public Map<String, Map<String, String>> getBaseDataMappingMap() {
        return this.baseDataMappingMap;
    }

    public void setBaseDataMappingMap(Map<String, Map<String, String>> baseDataMappingMap) {
        this.baseDataMappingMap = baseDataMappingMap;
    }
}

