/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.common.dto.fetch.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FetchRequestContextDTO {
    private String bblx;
    private String rpUnitType;
    private String unitCode;
    private String unitName;
    private String periodScheme;
    private String periodTitle;
    private String startDateStr;
    private String endDateStr;
    private String startAdjustPeriod;
    private String endAdjustPeriod;
    private Boolean includeUncharged;
    private Boolean includeAdjustVchr;
    private String formSchemeId;
    private String fetchSchemeId;
    private String formId;
    private String regionId;
    private String taskId;
    private Map<String, String> otherEntity;
    private String dimensionSetStr;
    private Map<String, String> extParam;
    private String formulaSchemeKeys;

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getRpUnitType() {
        return this.rpUnitType;
    }

    public void setRpUnitType(String rpUnitType) {
        this.rpUnitType = rpUnitType;
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

    public String getPeriodScheme() {
        return this.periodScheme;
    }

    public void setPeriodScheme(String periodScheme) {
        this.periodScheme = periodScheme;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
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

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
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

    public Map<String, String> getExtParam() {
        return this.extParam;
    }

    public void setExtParam(Map<String, String> extParam) {
        this.extParam = extParam;
    }

    public String getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(String formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public Boolean getIncludeAdjustVchr() {
        return this.includeAdjustVchr;
    }

    public void setIncludeAdjustVchr(Boolean includeAdjustVchr) {
        this.includeAdjustVchr = includeAdjustVchr;
    }

    public String toString() {
        return "FetchRequestContextDTO [bblx=" + this.bblx + ", rpUnitType=" + this.rpUnitType + ", unitCode=" + this.unitCode + ", unitName=" + this.unitName + ", periodScheme=" + this.periodScheme + ", periodTitle=" + this.periodTitle + ", startDateStr=" + this.startDateStr + ", endDateStr=" + this.endDateStr + ", startAdjustPeriod=" + this.startAdjustPeriod + ", endAdjustPeriod=" + this.endAdjustPeriod + ", includeUncharged=" + this.includeUncharged + ", includeAdjustVchr=" + this.includeAdjustVchr + ", formSchemeId=" + this.formSchemeId + ", fetchSchemeId=" + this.fetchSchemeId + ", formId=" + this.formId + ", regionId=" + this.regionId + ", taskId=" + this.taskId + ", otherEntity=" + this.otherEntity + ", dimensionSetStr=" + this.dimensionSetStr + ", extParam=" + this.extParam + ", formulaSchemeKeys=" + this.formulaSchemeKeys + "]";
    }
}

