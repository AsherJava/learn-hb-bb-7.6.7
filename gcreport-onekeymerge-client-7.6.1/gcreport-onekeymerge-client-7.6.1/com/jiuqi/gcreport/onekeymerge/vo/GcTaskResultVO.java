/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.gcreport.onekeymerge.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class GcTaskResultVO {
    private Integer taskIndex;
    private String orgId;
    private String orgType;
    private String taskId;
    private String schemeId;
    private String currency;
    private Integer acctYear;
    private Integer acctPeriod;
    private Integer periodType;
    private Integer taskState;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss")
    private Date taskTime;
    private String userName;
    private String taskData;
    private String taskLog;

    public Integer getTaskIndex() {
        return this.taskIndex;
    }

    public void setTaskIndex(Integer taskIndex) {
        this.taskIndex = taskIndex;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
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

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public Integer getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public Integer getTaskState() {
        return this.taskState;
    }

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public Date getTaskTime() {
        return this.taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public String getTaskData() {
        return this.taskData;
    }

    public String getTaskLog() {
        return this.taskLog;
    }

    public void setTaskLog(String taskLog) {
        this.taskLog = taskLog;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

