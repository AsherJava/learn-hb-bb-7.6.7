/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.logmanager.impl.intf;

public class FetchSqlLogDTO {
    private String sqlInfoId;
    private String title;
    private String startTime;
    private String endTime;
    private Double costTime;
    private String executeParam;
    private String sql;

    public String getSqlInfoId() {
        return this.sqlInfoId;
    }

    public void setSqlInfoId(String sqlInfoId) {
        this.sqlInfoId = sqlInfoId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Double getCostTime() {
        return this.costTime;
    }

    public void setCostTime(Double costTime) {
        this.costTime = costTime;
    }

    public String getExecuteParam() {
        return this.executeParam;
    }

    public void setExecuteParam(String executeParam) {
        this.executeParam = executeParam;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}

