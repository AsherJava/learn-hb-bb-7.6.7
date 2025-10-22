/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.reportdatasync.vo;

import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.List;

public class ReportDataSyncUploadCheckParamVO {
    private String taskId;
    private String taskCode;
    private String taskTitle;
    private List<String> schemeKeys;
    private String syncVersion;
    private String orgCode;
    private String orgTitle;
    private String periodStr;
    private String periodStrValue;
    private String SelectAdjust;
    private Boolean applicationMode = Boolean.TRUE;
    private String targetUrl;
    private List<GcOrgCacheVO> unitVos;
    private List<String> orgCodes;
    private Boolean unitVosCheck = Boolean.TRUE;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSyncVersion() {
        return this.syncVersion;
    }

    public void setSyncVersion(String syncVersion) {
        this.syncVersion = syncVersion;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getPeriodStrValue() {
        return this.periodStrValue;
    }

    public void setPeriodStrValue(String periodStrValue) {
        this.periodStrValue = periodStrValue;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public List<String> getSchemeKeys() {
        return this.schemeKeys;
    }

    public void setSchemeKeys(List<String> schemeKeys) {
        this.schemeKeys = schemeKeys;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public Boolean getApplicationMode() {
        return this.applicationMode;
    }

    public void setApplicationMode(Boolean applicationMode) {
        this.applicationMode = applicationMode;
    }

    public String getTargetUrl() {
        return this.targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public List<GcOrgCacheVO> getUnitVos() {
        return this.unitVos;
    }

    public void setUnitVos(List<GcOrgCacheVO> unitVos) {
        this.unitVos = unitVos;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public Boolean getUnitVosCheck() {
        return this.unitVosCheck;
    }

    public void setUnitVosCheck(Boolean unitVosCheck) {
        this.unitVosCheck = unitVosCheck;
    }

    public String getSelectAdjust() {
        return this.SelectAdjust;
    }

    public void setSelectAdjust(String selectAdjust) {
        this.SelectAdjust = selectAdjust;
    }
}

