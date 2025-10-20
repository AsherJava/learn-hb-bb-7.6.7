/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.dto;

import java.util.Date;

public class MergeTaskProcessDTO {
    private String taskState;
    private String dataTime;
    private String taskCodes;
    private String orgId;
    private Date createTime;
    private String userName;
    private Date finishTime;
    private Double process;
    private String dims;
    private String nrTaskId;
    private Long totalTaskCount;
    private Long finishedTaskCount;

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

    public String getDims() {
        return this.dims;
    }

    public void setDims(String dims) {
        this.dims = dims;
    }

    public String getNrTaskId() {
        return this.nrTaskId;
    }

    public void setNrTaskId(String nrTaskId) {
        this.nrTaskId = nrTaskId;
    }

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

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

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}

