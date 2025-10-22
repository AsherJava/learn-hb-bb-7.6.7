/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum
 *  com.jiuqi.nvwa.nlpr.resource.DimensionValue
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto;

import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;
import com.jiuqi.nvwa.nlpr.resource.DimensionValue;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class FinancialFetchCondition
implements Serializable {
    private static final long serialVersionUID = -6703212137474098428L;
    private FinancialCubesPeriodTypeEnum periodType;
    private String orgCode;
    private Integer startBatchNum;
    private Integer endBatchNum;
    private Map<String, DimensionValue> dimensionValueMap;
    private List<String> mainDimCode;
    private String dataTime;
    private String currencyCode;
    private List<String> rebuildScope;
    private String fetchSchemeId;
    private String formSchemeId;
    private String fetchSchemeName;
    private String formSchemeName;
    private String orgType;
    private String taskId;

    public List<String> getMainDimCode() {
        return this.mainDimCode;
    }

    public void setMainDimCode(List<String> mainDimCode) {
        this.mainDimCode = mainDimCode;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public FinancialCubesPeriodTypeEnum getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(FinancialCubesPeriodTypeEnum periodType) {
        this.periodType = periodType;
    }

    public Integer getStartBatchNum() {
        return this.startBatchNum;
    }

    public void setStartBatchNum(Integer startBatchNum) {
        this.startBatchNum = startBatchNum;
    }

    public Integer getEndBatchNum() {
        return this.endBatchNum;
    }

    public void setEndBatchNum(Integer endBatchNum) {
        this.endBatchNum = endBatchNum;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public Map<String, DimensionValue> getDimensionValueMap() {
        return this.dimensionValueMap;
    }

    public void setDimensionValueMap(Map<String, DimensionValue> dimensionValueMap) {
        this.dimensionValueMap = dimensionValueMap;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<String> getRebuildScope() {
        return this.rebuildScope;
    }

    public void setRebuildScope(List<String> rebuildScope) {
        this.rebuildScope = rebuildScope;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getFetchSchemeName() {
        return this.fetchSchemeName;
    }

    public void setFetchSchemeName(String fetchSchemeName) {
        this.fetchSchemeName = fetchSchemeName;
    }

    public String getFormSchemeName() {
        return this.formSchemeName;
    }

    public void setFormSchemeName(String formSchemeName) {
        this.formSchemeName = formSchemeName;
    }
}

