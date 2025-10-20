/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.logmanager.client;

import java.sql.Timestamp;

public class LogManagerInfoItemVO {
    private String id;
    private String requestInstcId;
    private String requestTaskId;
    private String formId;
    private String formName;
    private Integer formTaskCount;
    private Integer failureCount;
    private Integer successCount;
    private Double process;
    private Integer executeState;
    private Timestamp createTime;
    private Timestamp startTime;
    private Timestamp endTime;
    private Integer storeType;
    private String taskSource;
    private String dimType;
    private String dimCode;
    private String resultLog;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestInstcId() {
        return this.requestInstcId;
    }

    public void setRequestInstcId(String requestInstcId) {
        this.requestInstcId = requestInstcId;
    }

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFormName() {
        return this.formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Integer getFormTaskCount() {
        return this.formTaskCount;
    }

    public void setFormTaskCount(Integer formTaskCount) {
        this.formTaskCount = formTaskCount;
    }

    public Integer getFailureCount() {
        return this.failureCount == null ? 0 : this.failureCount;
    }

    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    public Integer getSuccessCount() {
        return this.successCount == null ? 0 : this.successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Double getProcess() {
        return this.process;
    }

    public void setProcess(Double process) {
        this.process = process;
    }

    public Integer getExecuteState() {
        return this.executeState;
    }

    public void setExecuteState(Integer executeState) {
        this.executeState = executeState;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getStoreType() {
        return this.storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public String getTaskSource() {
        return this.taskSource;
    }

    public void setTaskSource(String taskSource) {
        this.taskSource = taskSource;
    }

    public String getResultLog() {
        return this.resultLog;
    }

    public void setResultLog(String resultLog) {
        this.resultLog = resultLog;
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

    public String toString() {
        return "LogManagerInfoItemVO [id=" + this.id + ", requestInstcId=" + this.requestInstcId + ", requestTaskId=" + this.requestTaskId + ", formId=" + this.formId + ", formName=" + this.formName + ", formTaskCount=" + this.formTaskCount + ", failureCount=" + this.failureCount + ", successCount=" + this.successCount + ", process=" + this.process + ", executeState=" + this.executeState + ", createTime=" + this.createTime + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", storeType=" + this.storeType + ", taskSource=" + this.taskSource + ", dimType=" + this.dimType + ", dimCode=" + this.dimCode + ", resultLog=" + this.resultLog + "]";
    }
}

