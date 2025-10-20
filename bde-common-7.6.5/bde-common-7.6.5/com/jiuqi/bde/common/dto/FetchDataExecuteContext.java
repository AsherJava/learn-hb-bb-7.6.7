/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.BizModelExtFieldInfo;
import com.jiuqi.bde.common.dto.ExecuteSettingVO;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.dto.fetch.request.FetchRequestFloatSettingDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonInclude(value=JsonInclude.Include.NON_EMPTY)
public class FetchDataExecuteContext {
    private String computationModelCode;
    private String bizType;
    private String requestSourceType;
    private String requestTaskId;
    private Integer routeNum;
    private String bblx;
    private String unitCode;
    private String unitName;
    private String currency;
    private String periodScheme;
    private String startDateStr;
    private String startAdjustPeriod;
    private String endAdjustPeriod;
    private String endDateStr;
    private Boolean includeUncharged;
    private Boolean includeAdjustVchr;
    private String formSchemeId;
    private String fetchSchemeId;
    private String formId;
    private String regionId;
    private String taskId;
    private Map<String, String> otherEntity;
    private OrgMappingDTO orgMapping;
    private FetchRequestFloatSettingDTO floatSetting;
    private String dimensionSetStr;
    private List<ExecuteSettingVO> fixedSettingList = new ArrayList<ExecuteSettingVO>(32);
    private String optimizeRuleGroup;
    private String rpUnitType;
    private Map<String, String> extParam;
    private BizModelExtFieldInfo bizModelExtFieldInfo;

    public String getComputationModelCode() {
        return this.computationModelCode;
    }

    public void setComputationModelCode(String computationModelCode) {
        this.computationModelCode = computationModelCode;
    }

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getRequestSourceType() {
        return this.requestSourceType;
    }

    public void setRequestSourceType(String requestSourceType) {
        this.requestSourceType = requestSourceType;
    }

    public Integer getRouteNum() {
        return this.routeNum;
    }

    public void setRouteNum(Integer routeNum) {
        this.routeNum = routeNum;
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
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

    public OrgMappingDTO getOrgMapping() {
        return this.orgMapping;
    }

    public void setOrgMapping(OrgMappingDTO orgMapping) {
        this.orgMapping = orgMapping;
    }

    public FetchRequestFloatSettingDTO getFloatSetting() {
        return this.floatSetting;
    }

    public void setFloatSetting(FetchRequestFloatSettingDTO floatSetting) {
        this.floatSetting = floatSetting;
    }

    public List<ExecuteSettingVO> getFixedSettingList() {
        return this.fixedSettingList;
    }

    public void setFixedSettingList(List<ExecuteSettingVO> fixedSettingList) {
        this.fixedSettingList = fixedSettingList;
    }

    public void addFixedSetting(ExecuteSettingVO fixedSetting) {
        this.fixedSettingList.add(fixedSetting);
    }

    public String getOptimizeRuleGroup() {
        return this.optimizeRuleGroup;
    }

    public void setOptimizeRuleGroup(String optimizeRuleGroup) {
        this.optimizeRuleGroup = optimizeRuleGroup;
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

    public String getRpUnitType() {
        return this.rpUnitType;
    }

    public void setRpUnitType(String rpUnitType) {
        this.rpUnitType = rpUnitType;
    }

    public Map<String, String> getExtParam() {
        return this.extParam;
    }

    public void setExtParam(Map<String, String> extParam) {
        this.extParam = extParam;
    }

    public BizModelExtFieldInfo getBizModelExtFieldInfo() {
        return this.bizModelExtFieldInfo;
    }

    public void setBizModelExtFieldInfo(BizModelExtFieldInfo bizModelExtFieldInfo) {
        this.bizModelExtFieldInfo = bizModelExtFieldInfo;
    }

    public Boolean getIncludeAdjustVchr() {
        return this.includeAdjustVchr;
    }

    public void setIncludeAdjustVchr(Boolean includeAdjustVchr) {
        this.includeAdjustVchr = includeAdjustVchr;
    }

    public String toString() {
        return "FetchDataExecuteContext [bizType=" + this.bizType + ", requestSourceType=" + this.requestSourceType + ", requestTaskId=" + this.requestTaskId + ", routeNum=" + this.routeNum + ", bblx=" + this.bblx + ", unitCode=" + this.unitCode + ", unitName=" + this.unitName + ", currency=" + this.currency + ", periodScheme=" + this.periodScheme + ", startDateStr=" + this.startDateStr + ", startAdjustPeriod=" + this.startAdjustPeriod + ", endAdjustPeriod=" + this.endAdjustPeriod + ", endDateStr=" + this.endDateStr + ", includeUncharged=" + this.includeUncharged + ", includeAdjustVchr=" + this.includeAdjustVchr + ", formSchemeId=" + this.formSchemeId + ", fetchSchemeId=" + this.fetchSchemeId + ", formId=" + this.formId + ", regionId=" + this.regionId + ", taskId=" + this.taskId + ", otherEntity=" + this.otherEntity + ", orgMapping=" + this.orgMapping + ", floatSetting=" + this.floatSetting + ", dimensionSetStr=" + this.dimensionSetStr + ", fixedSettingList=" + this.fixedSettingList + ", optimizeRuleGroup=" + this.optimizeRuleGroup + ", rpUnitType=" + this.rpUnitType + ", extParam=" + this.extParam + ", bizModelExtParam=" + this.bizModelExtFieldInfo + "]";
    }
}

