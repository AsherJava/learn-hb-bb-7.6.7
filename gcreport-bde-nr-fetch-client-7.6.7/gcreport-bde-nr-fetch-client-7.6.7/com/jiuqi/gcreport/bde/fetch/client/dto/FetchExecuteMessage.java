/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRangResult
 */
package com.jiuqi.gcreport.bde.fetch.client.dto;

import com.jiuqi.bde.common.dto.fetch.init.FetchRangResult;
import java.util.Map;

public class FetchExecuteMessage {
    private String requestSourceType;
    private String asyncTaskId;
    private String groupId;
    private String requestRunnerId;
    private String requestInstcId;
    private String requestTaskId;
    private String rpUnitType;
    private String bblx;
    private String unitCode;
    private String unitName;
    private String currency;
    private String periodScheme;
    private String startDateStr;
    private String endDateStr;
    private String startAdjustPeriod;
    private String endAdjustPeriod;
    private Boolean includeUncharged;
    private Boolean includeAdjustVchr;
    private String formSchemeId;
    private String formSchemeTitle;
    private String fetchSchemeId;
    private String fetchSchemeTitle;
    private String taskId;
    private Map<String, String> otherEntity;
    private String dimensionSetStr;
    private String formulaSchemeKeys;
    private String userName;
    private String formId;
    private String regionId;
    FetchRangResult fetchRangResult;
    private Boolean forceSkipEtlHandle;

    public String getRequestSourceType() {
        return this.requestSourceType;
    }

    public void setRequestSourceType(String requestSourceType) {
        this.requestSourceType = requestSourceType;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getRequestRunnerId() {
        return this.requestRunnerId;
    }

    public void setRequestRunnerId(String requestRunnerId) {
        this.requestRunnerId = requestRunnerId;
    }

    public String getRequestInstcId() {
        return this.requestInstcId;
    }

    public void setRequestInstcId(String requestInstcId) {
        this.requestInstcId = requestInstcId;
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getRpUnitType() {
        return this.rpUnitType;
    }

    public void setRpUnitType(String rpUnitType) {
        this.rpUnitType = rpUnitType;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPeriodScheme() {
        return this.periodScheme;
    }

    public void setPeriodScheme(String periodScheme) {
        this.periodScheme = periodScheme;
    }

    public String getStartDateStr() {
        return this.startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return this.endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public Boolean getIncludeUncharged() {
        return this.includeUncharged;
    }

    public void setIncludeUncharged(Boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getFetchSchemeTitle() {
        return this.fetchSchemeTitle;
    }

    public void setFetchSchemeTitle(String fetchSchemeTitle) {
        this.fetchSchemeTitle = fetchSchemeTitle;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, String> getOtherEntity() {
        return this.otherEntity;
    }

    public void setOtherEntity(Map<String, String> otherEntity) {
        this.otherEntity = otherEntity;
    }

    public String getDimensionSetStr() {
        return this.dimensionSetStr;
    }

    public void setDimensionSetStr(String dimensionSetStr) {
        this.dimensionSetStr = dimensionSetStr;
    }

    public String getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(String formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getStartAdjustPeriod() {
        return this.startAdjustPeriod;
    }

    public void setStartAdjustPeriod(String startAdjustPeriod) {
        this.startAdjustPeriod = startAdjustPeriod;
    }

    public String getEndAdjustPeriod() {
        return this.endAdjustPeriod;
    }

    public void setEndAdjustPeriod(String endAdjustPeriod) {
        this.endAdjustPeriod = endAdjustPeriod;
    }

    public FetchRangResult getFetchRangResult() {
        return this.fetchRangResult;
    }

    public void setFetchRangResult(FetchRangResult fetchRangResult) {
        this.fetchRangResult = fetchRangResult;
    }

    public Boolean isForceSkipEtlHandle() {
        return this.forceSkipEtlHandle;
    }

    public void setForceSkipEtlHandle(Boolean forceSkipEtlHandle) {
        this.forceSkipEtlHandle = forceSkipEtlHandle;
    }

    public Boolean getIncludeAdjustVchr() {
        return this.includeAdjustVchr;
    }

    public void setIncludeAdjustVchr(Boolean includeAdjustVchr) {
        this.includeAdjustVchr = includeAdjustVchr;
    }

    public String toString() {
        return "FetchExecuteMessage [requestSourceType=" + this.requestSourceType + ", asyncTaskId=" + this.asyncTaskId + ", groupId=" + this.groupId + ", requestRunnerId=" + this.requestRunnerId + ", requestInstcId=" + this.requestInstcId + ", requestTaskId=" + this.requestTaskId + ", bblx=" + this.bblx + ", unitCode=" + this.unitCode + ", unitName=" + this.unitName + ", currency=" + this.currency + ", periodScheme=" + this.periodScheme + ", startDateStr=" + this.startDateStr + ", endDateStr=" + this.endDateStr + ", startAdjustPeriod=" + this.startAdjustPeriod + ", endAdjustPeriod=" + this.endAdjustPeriod + ", includeAdjustVchr=" + this.includeAdjustVchr + ", includeUncharged=" + this.includeUncharged + ", formSchemeId=" + this.formSchemeId + ", formSchemeTitle=" + this.formSchemeTitle + ", fetchSchemeId=" + this.fetchSchemeId + ", fetchSchemeTitle=" + this.fetchSchemeTitle + ", taskId=" + this.taskId + ", otherEntity=" + this.otherEntity + ", dimensionSetStr=" + this.dimensionSetStr + ", userName=" + this.userName + ", formId=" + this.formId + ", regionId=" + this.regionId + ", rpUnitType=" + this.rpUnitType + ", fetchRangResult=" + this.fetchRangResult + ", forceSkipEtlHandle=" + this.forceSkipEtlHandle + "]";
    }
}

