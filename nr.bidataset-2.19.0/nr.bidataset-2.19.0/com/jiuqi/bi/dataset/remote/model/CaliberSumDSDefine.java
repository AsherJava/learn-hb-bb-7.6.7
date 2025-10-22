/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset.remote.model;

import java.util.List;
import org.json.JSONObject;

public class CaliberSumDSDefine {
    private String taskId;
    private String taskTitle;
    private String caliberId;
    private String caliberTitle;
    private List sumZbs;
    private Boolean auditErr;
    private Boolean reportStatus;
    private Boolean dataMerge;
    private Boolean onlyReportUnit;
    private String caliberMapRule;

    public void toJSON(JSONObject ds) {
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

    public String getCaliberId() {
        return this.caliberId;
    }

    public void setCaliberId(String caliberId) {
        this.caliberId = caliberId;
    }

    public String getCaliberTitle() {
        return this.caliberTitle;
    }

    public void setCaliberTitle(String caliberTitle) {
        this.caliberTitle = caliberTitle;
    }

    public List getSumZbs() {
        return this.sumZbs;
    }

    public void setSumZbs(List sumZbs) {
        this.sumZbs = sumZbs;
    }

    public Boolean getAuditErr() {
        return this.auditErr;
    }

    public void setAuditErr(Boolean auditErr) {
        this.auditErr = auditErr;
    }

    public Boolean getReportStatus() {
        return this.reportStatus;
    }

    public void setReportStatus(Boolean reportStatus) {
        this.reportStatus = reportStatus;
    }

    public Boolean getDataMerge() {
        return this.dataMerge;
    }

    public void setDataMerge(Boolean dataMerge) {
        this.dataMerge = dataMerge;
    }

    public String getCaliberMapRule() {
        return this.caliberMapRule;
    }

    public void setCaliberMapRule(String caliberMapRule) {
        this.caliberMapRule = caliberMapRule;
    }

    public Boolean getOnlyReportUnit() {
        return this.onlyReportUnit;
    }

    public void setOnlyReportUnit(Boolean onlyReportUnit) {
        this.onlyReportUnit = onlyReportUnit;
    }
}

