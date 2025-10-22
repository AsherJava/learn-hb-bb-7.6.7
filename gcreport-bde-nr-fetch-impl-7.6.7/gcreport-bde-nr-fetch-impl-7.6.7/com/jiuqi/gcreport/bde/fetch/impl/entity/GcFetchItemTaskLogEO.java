/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.entity;

import java.util.Date;

public class GcFetchItemTaskLogEO {
    public static final String TABLENAME = "GC_FETCH_ITEMTASKLOG";
    private String id;
    private String fetchTaskId;
    private String formId;
    private String regionId;
    private String executeState;
    private Double process;
    private Date processTime;
    private String resultContent;
    private String errorLog;
    private Integer fetchStatus;

    public String getErrorLog() {
        return this.errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }

    public String getFetchTaskId() {
        return this.fetchTaskId;
    }

    public void setFetchTaskId(String fetchTaskId) {
        this.fetchTaskId = fetchTaskId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getExecuteState() {
        return this.executeState;
    }

    public void setExecuteState(String executeState) {
        this.executeState = executeState;
    }

    public Double getProcess() {
        return this.process;
    }

    public void setProcess(Double process) {
        this.process = process;
    }

    public Date getProcessTime() {
        return this.processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public String getResultContent() {
        return this.resultContent;
    }

    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFetchStatus() {
        return this.fetchStatus;
    }

    public void setFetchStatus(Integer fetchStatus) {
        this.fetchStatus = fetchStatus;
    }
}

