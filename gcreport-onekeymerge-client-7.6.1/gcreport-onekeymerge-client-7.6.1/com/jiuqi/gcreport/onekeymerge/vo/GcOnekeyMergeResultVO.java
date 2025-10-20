/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.vo;

import com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO;
import java.util.Map;

public class GcOnekeyMergeResultVO {
    private String id;
    private String taskCodes;
    private String orgId;
    private String orgType;
    private String taskId;
    private String schemeId;
    private String currency;
    private Integer acctYear;
    private Integer acctPeriod;
    private Integer periodType;
    private Integer taskState;
    private String taskTime;
    private String userName;
    private String lastTask;
    private Map<String, GcTaskResultVO> taskResult;

    public String getTaskCodes() {
        return this.taskCodes;
    }

    public void setTaskCodes(String taskCodes) {
        this.taskCodes = taskCodes;
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

    public void setTaskState(Integer taskState) {
        this.taskState = taskState;
    }

    public String getTaskTime() {
        return this.taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Map<String, GcTaskResultVO> getTaskResult() {
        return this.taskResult;
    }

    public void setTaskResult(Map<String, GcTaskResultVO> taskResult) {
        this.taskResult = taskResult;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLastTask() {
        return this.lastTask;
    }

    public void setLastTask(String lastTask) {
        this.lastTask = lastTask;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTaskState() {
        return this.taskState;
    }
}

