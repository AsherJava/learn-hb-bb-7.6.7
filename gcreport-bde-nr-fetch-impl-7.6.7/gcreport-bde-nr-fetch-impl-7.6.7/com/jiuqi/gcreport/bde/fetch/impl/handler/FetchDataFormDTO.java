/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO
 */
package com.jiuqi.gcreport.bde.fetch.impl.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchRegionDTO;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FetchDataFormDTO {
    private String userName;
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
    private String formId;
    private String formCode;
    private String formTitle;
    private int fetchFormCt;
    private List<FetchRegionDTO> fetchRegions;
    private Boolean forceSkipEtlHandle;
    private OrgMappingDTO orgMapping;

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

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public int getFetchFormCt() {
        return this.fetchFormCt;
    }

    public void setFetchFormCt(int fetchFormCt) {
        this.fetchFormCt = fetchFormCt;
    }

    public List<FetchRegionDTO> getFetchRegions() {
        return this.fetchRegions;
    }

    public void setFetchRegions(List<FetchRegionDTO> fetchRegions) {
        this.fetchRegions = fetchRegions;
    }

    public Boolean getForceSkipEtlHandle() {
        return this.forceSkipEtlHandle;
    }

    public void setForceSkipEtlHandle(Boolean forceSkipEtlHandle) {
        this.forceSkipEtlHandle = forceSkipEtlHandle;
    }

    public OrgMappingDTO getOrgMapping() {
        return this.orgMapping;
    }

    public void setOrgMapping(OrgMappingDTO orgMapping) {
        this.orgMapping = orgMapping;
    }

    public Boolean getIncludeAdjustVchr() {
        return this.includeAdjustVchr;
    }

    public void setIncludeAdjustVchr(Boolean includeAdjustVchr) {
        this.includeAdjustVchr = includeAdjustVchr;
    }

    public String toString() {
        return "FetchDataFormDTO [userName=" + this.userName + ", rpUnitType=" + this.rpUnitType + ", bblx=" + this.bblx + ", unitCode=" + this.unitCode + ", unitName=" + this.unitName + ", currency=" + this.currency + ", periodScheme=" + this.periodScheme + ", startDateStr=" + this.startDateStr + ", endDateStr=" + this.endDateStr + ", startAdjustPeriod=" + this.startAdjustPeriod + ", endAdjustPeriod=" + this.endAdjustPeriod + ", includeUncharged=" + this.includeUncharged + ", includeAdjustVchr=" + this.includeAdjustVchr + ", formSchemeId=" + this.formSchemeId + ", formSchemeTitle=" + this.formSchemeTitle + ", fetchSchemeId=" + this.fetchSchemeId + ", fetchSchemeTitle=" + this.fetchSchemeTitle + ", taskId=" + this.taskId + ", otherEntity=" + this.otherEntity + ", dimensionSetStr=" + this.dimensionSetStr + ", formulaSchemeKeys=" + this.formulaSchemeKeys + ", formId=" + this.formId + ", formCode=" + this.formCode + ", formTitle=" + this.formTitle + ", fetchFormCt=" + this.fetchFormCt + ", fetchRegions=" + this.fetchRegions + ", forceSkipEtlHandle=" + this.forceSkipEtlHandle + "]";
    }
}

