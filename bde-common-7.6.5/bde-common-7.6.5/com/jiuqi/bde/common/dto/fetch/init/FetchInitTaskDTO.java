/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.bde.common.dto.fetch.init;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchFormDTO;
import com.jiuqi.bde.common.dto.fetch.init.FetchRangResult;
import com.jiuqi.common.base.util.CollectionUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FetchInitTaskDTO {
    private String requestRunnerId;
    private String requestSourceType;
    private String rpUnitType;
    private String bblx;
    private String unitCode;
    private String unitName;
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
    private String taskTitle;
    private List<String> fetchFormKeyList;
    private List<FetchFormDTO> fetchForms;
    private Map<String, String> otherEntity;
    private String dimensionSetStr;
    private Map<String, Object> variableMap;
    private String formulaSchemeKeys;
    private String currency;
    private String username;
    private FetchRangResult fetchRangResult;
    private Boolean forceSkipEtlHandle;
    private int fetchFormCt;
    private OrgMappingDTO orgMapping;

    public String getRequestSourceType() {
        return this.requestSourceType;
    }

    public void setRequestSourceType(String requestSourceType) {
        this.requestSourceType = requestSourceType;
    }

    public String getRequestRunnerId() {
        return this.requestRunnerId;
    }

    public void setRequestRunnerId(String requestRunnerId) {
        this.requestRunnerId = requestRunnerId;
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

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public List<String> getFetchFormKeyList() {
        return this.fetchFormKeyList;
    }

    public void setFetchFormKeyList(List<String> fetchFormKeyList) {
        this.fetchFormKeyList = fetchFormKeyList;
    }

    public List<FetchFormDTO> getFetchForms() {
        return this.fetchForms == null ? CollectionUtils.newArrayList() : this.fetchForms;
    }

    public void setFetchForms(List<FetchFormDTO> fetchForms) {
        this.fetchForms = fetchForms;
    }

    public int getFetchFormCt() {
        return this.fetchFormCt;
    }

    public void setFetchFormCt(int fetchFormCt) {
        this.fetchFormCt = fetchFormCt;
    }

    public Map<String, String> getOtherEntity() {
        return this.otherEntity == null ? new HashMap() : this.otherEntity;
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

    public Map<String, Object> getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }

    public String getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(String formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserName() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
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
        return "FetchInitTaskDTO [requestRunnerId=" + this.requestRunnerId + ", requestSourceType=" + this.requestSourceType + ", rpUnitType=" + this.rpUnitType + ", bblx=" + this.bblx + ", unitCode=" + this.unitCode + ", unitName=" + this.unitName + ", periodScheme=" + this.periodScheme + ", startDateStr=" + this.startDateStr + ", endDateStr=" + this.endDateStr + ", startAdjustPeriod=" + this.startAdjustPeriod + ", endAdjustPeriod=" + this.endAdjustPeriod + ", includeUncharged=" + this.includeUncharged + ", includeAdjustVchr=" + this.includeAdjustVchr + ", formSchemeId=" + this.formSchemeId + ", formSchemeTitle=" + this.formSchemeTitle + ", fetchSchemeId=" + this.fetchSchemeId + ", fetchSchemeTitle=" + this.fetchSchemeTitle + ", taskId=" + this.taskId + ", taskTitle=" + this.taskTitle + ", fetchFormKeyList=" + this.fetchFormKeyList + ", fetchForms=" + this.fetchForms + ", otherEntity=" + this.otherEntity + ", dimensionSetStr=" + this.dimensionSetStr + ", variableMap=" + this.variableMap + ", formulaSchemeKeys=" + this.formulaSchemeKeys + ", currency=" + this.currency + ", username=" + this.username + ", fetchRangResult=" + this.fetchRangResult + ", forceSkipEtlHandle=" + this.forceSkipEtlHandle + ", fetchFormCt=" + this.fetchFormCt + "]";
    }
}

