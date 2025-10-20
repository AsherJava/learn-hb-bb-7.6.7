/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.intf;

import com.jiuqi.bde.common.dto.OrgMappingDTO;
import java.util.Map;

public class BaseFetchTaskInfo {
    private String callBackAddress;
    private String requestTaskId;
    private String unitCode;
    private String periodScheme;
    private String startDateStr;
    private String endDateStr;
    private String startAdjustPeriod;
    private String endAdjustPeriod;
    private Boolean includeUncharged = true;
    private Boolean includeAdjustVchr;
    private String bblx;
    private String fetchSchemeId;
    private String formSchemeId;
    private String taskId;
    private String formSchemeTitle;
    private String taskTitle;
    private Map<String, String> otherEntity;
    private String dimensionSetStr;
    private Map<String, String> extParam;
    private String requestSourceType;
    private OrgMappingDTO orgMapping;
    private Integer routeNum;
    private String rpUnitType;

    public String getCallBackAddress() {
        return this.callBackAddress;
    }

    public void setCallBackAddress(String callBackAddress) {
        this.callBackAddress = callBackAddress;
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
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

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
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

    public Map<String, String> getExtParam() {
        return this.extParam;
    }

    public void setExtParam(Map<String, String> extParam) {
        this.extParam = extParam;
    }

    public String getRequestSourceType() {
        return this.requestSourceType;
    }

    public void setRequestSourceType(String requestSourceType) {
        this.requestSourceType = requestSourceType;
    }

    public OrgMappingDTO getOrgMapping() {
        return this.orgMapping;
    }

    public void setOrgMapping(OrgMappingDTO orgMapping) {
        this.orgMapping = orgMapping;
    }

    public Integer getRouteNum() {
        return this.routeNum;
    }

    public void setRouteNum(Integer routeNum) {
        this.routeNum = routeNum;
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

    public Boolean getIncludeAdjustVchr() {
        return this.includeAdjustVchr;
    }

    public void setIncludeAdjustVchr(Boolean includeAdjustVchr) {
        this.includeAdjustVchr = includeAdjustVchr;
    }

    public String toString() {
        return "BaseFetchTaskInfo{callBackAddress='" + this.callBackAddress + '\'' + ", requestTaskId='" + this.requestTaskId + '\'' + ", unitCode='" + this.unitCode + '\'' + ", periodScheme='" + this.periodScheme + '\'' + ", startDateStr='" + this.startDateStr + '\'' + ", endDateStr='" + this.endDateStr + '\'' + ", startAdjustPeriod='" + this.startAdjustPeriod + '\'' + ", endAdjustPeriod='" + this.endAdjustPeriod + '\'' + ", includeUncharged=" + this.includeUncharged + ", includeAdjustVchr=" + this.includeAdjustVchr + ", bblx='" + this.bblx + '\'' + ", fetchSchemeId='" + this.fetchSchemeId + '\'' + ", formSchemeId='" + this.formSchemeId + '\'' + ", taskId='" + this.taskId + '\'' + ", formSchemeTitle='" + this.formSchemeTitle + '\'' + ", taskTitle='" + this.taskTitle + '\'' + ", otherEntity=" + this.otherEntity + ", dimensionSetStr='" + this.dimensionSetStr + '\'' + ", requestSourceType='" + this.requestSourceType + '\'' + ", orgMapping=" + this.orgMapping + ", routeNum=" + this.routeNum + ", rpUnitType=" + this.rpUnitType + '}';
    }
}

