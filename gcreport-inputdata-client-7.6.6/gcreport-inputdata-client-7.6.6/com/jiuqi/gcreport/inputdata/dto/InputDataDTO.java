/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.inputdata.dto;

import java.util.Date;

public class InputDataDTO {
    private String Id;
    private String taskId;
    private String mdOrg;
    private String oppUnitId;
    private String subjectCode;
    private String offsetState;
    private Date offsetTime;
    private String offsetGroupId;
    private Double amt;

    public String getId() {
        return this.Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getMdOrg() {
        return this.mdOrg;
    }

    public void setMdOrg(String mdOrg) {
        this.mdOrg = mdOrg;
    }

    public String getOppUnitId() {
        return this.oppUnitId;
    }

    public void setOppUnitId(String oppUnitId) {
        this.oppUnitId = oppUnitId;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getOffsetState() {
        return this.offsetState;
    }

    public void setOffsetState(String offsetState) {
        this.offsetState = offsetState;
    }

    public Date getOffsetTime() {
        return this.offsetTime;
    }

    public void setOffsetTime(Date offsetTime) {
        this.offsetTime = offsetTime;
    }

    public String getOffsetGroupId() {
        return this.offsetGroupId;
    }

    public void setOffsetGroupId(String offsetGroupId) {
        this.offsetGroupId = offsetGroupId;
    }

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }
}

