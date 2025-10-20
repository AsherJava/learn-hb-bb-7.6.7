/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.carryover.vo;

import java.util.Date;

public class CarryOverTaskProcessVO {
    private String nrTaskId;
    private String acctYear;
    private String carryOverSchemeId;
    private String targetSystemId;
    private String orgId;
    private String userName;
    private String taskState;
    private Date createTime;
    private Date finishTime;
    private Double process;
    private Long totalTaskCount;
    private Long finishedTaskCount;

    public String getNrTaskId() {
        return this.nrTaskId;
    }

    public void setNrTaskId(String nrTaskId) {
        this.nrTaskId = nrTaskId;
    }

    public String getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(String acctYear) {
        this.acctYear = acctYear;
    }

    public String getCarryOverSchemeId() {
        return this.carryOverSchemeId;
    }

    public void setCarryOverSchemeId(String carryOverSchemeId) {
        this.carryOverSchemeId = carryOverSchemeId;
    }

    public String getTargetSystemId() {
        return this.targetSystemId;
    }

    public void setTargetSystemId(String targetSystemId) {
        this.targetSystemId = targetSystemId;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Double getProcess() {
        return this.process;
    }

    public void setProcess(Double process) {
        this.process = process;
    }

    public Long getTotalTaskCount() {
        return this.totalTaskCount;
    }

    public void setTotalTaskCount(Long totalTaskCount) {
        this.totalTaskCount = totalTaskCount;
    }

    public Long getFinishedTaskCount() {
        return this.finishedTaskCount;
    }

    public void setFinishedTaskCount(Long finishedTaskCount) {
        this.finishedTaskCount = finishedTaskCount;
    }
}

