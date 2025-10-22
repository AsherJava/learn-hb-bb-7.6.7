/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.entity;

public class BatchBdeFetchLog {
    private String requestTaskId;
    private String log;
    private String unitCode;
    private String unitName;
    private String asyncTaskId;
    private String period;
    private String currency;

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String toString() {
        return "BatchBdeFetchLog{requestTaskId='" + this.requestTaskId + '\'' + ", log='" + this.log + '\'' + ", unitCode='" + this.unitCode + '\'' + ", unitName='" + this.unitName + '\'' + ", monitorId='" + this.asyncTaskId + '\'' + ", period='" + this.period + '\'' + ", currency='" + this.currency + '\'' + '}';
    }
}

