/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.dc.taskscheduling.logquery.client.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Deprecated
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class LogManagerDetailVO {
    private String id;
    private String taskType;
    private String instanceId;
    private String prenodeId;
    private String message;
    private Integer executeState;
    private Double progress;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String dimType;
    private String dimCode;
    private String resultLog;
    private Integer storeType;
    private String runnerId;
    private String queueName;
    private String executeAppName;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getPrenodeId() {
        return this.prenodeId;
    }

    public void setPrenodeId(String prenodeId) {
        this.prenodeId = prenodeId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getExecuteState() {
        return this.executeState;
    }

    public void setExecuteState(Integer executeState) {
        this.executeState = executeState;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDimType() {
        return this.dimType;
    }

    public void setDimType(String dimType) {
        this.dimType = dimType;
    }

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public String getResultLog() {
        return this.resultLog;
    }

    public void setResultLog(String resultLog) {
        this.resultLog = resultLog;
    }

    public Integer getStoreType() {
        return this.storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public Double getProgress() {
        return this.progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getExecuteAppName() {
        return this.executeAppName;
    }

    public void setExecuteAppName(String executeAppName) {
        this.executeAppName = executeAppName;
    }
}

