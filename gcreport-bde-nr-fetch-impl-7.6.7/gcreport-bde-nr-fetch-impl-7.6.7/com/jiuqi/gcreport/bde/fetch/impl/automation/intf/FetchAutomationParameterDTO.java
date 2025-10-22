/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.gcreport.bde.fetch.impl.automation.intf;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;

public class FetchAutomationParameterDTO {
    private String etlKey;
    private String user;
    private String unitCode;
    private String taskKey;
    private String schemeKey;
    private String dataTime;
    private String formKey;
    private String currency;
    private String orgType;
    private boolean includeUncharged;
    private boolean includeAdjustVchr;
    private boolean account;
    private Map<String, DimensionValue> dimensionSet;
    private Map<String, Object> variableMap;

    public String getEtlKey() {
        return this.etlKey;
    }

    public void setEtlKey(String etlKey) {
        this.etlKey = etlKey;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public boolean isIncludeUncharged() {
        return this.includeUncharged;
    }

    public void setIncludeUncharged(boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }

    public boolean isAccount() {
        return this.account;
    }

    public void setAccount(boolean account) {
        this.account = account;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public boolean isIncludeAdjustVchr() {
        return this.includeAdjustVchr;
    }

    public void setIncludeAdjustVchr(boolean includeAdjustVchr) {
        this.includeAdjustVchr = includeAdjustVchr;
    }

    public String toString() {
        return "FetchAutomationParameterPojo{etlKey='" + this.etlKey + '\'' + ", user='" + this.user + '\'' + ", unitCode='" + this.unitCode + '\'' + ", taskKey='" + this.taskKey + '\'' + ", schemeKey='" + this.schemeKey + '\'' + ", dataTime='" + this.dataTime + '\'' + ", formKey='" + this.formKey + '\'' + ", currency='" + this.currency + '\'' + ", orgType='" + this.orgType + '\'' + ", includeUncharged=" + this.includeUncharged + ", includeAdjustVchr=" + this.includeAdjustVchr + ", account=" + this.account + ", dimensionSet=" + this.dimensionSet + ", variableMap=" + this.variableMap + '}';
    }
}

