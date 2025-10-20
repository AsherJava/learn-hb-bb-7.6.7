/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.dc.taskscheduling.log.impl.domain;

import com.jiuqi.common.base.util.UUIDUtils;

public class SqlExecuteArchiveLogDO {
    private String id;
    private String logId;
    private String sqlInfoId;
    private String executeParam;
    private long startTime;
    private long endTime;

    public SqlExecuteArchiveLogDO() {
        this.id = UUIDUtils.newHalfGUIDStr();
    }

    public SqlExecuteArchiveLogDO(String id, String logId, String sqlInfoId, String executeParam, long startTime, long endTime) {
        this.id = id;
        this.logId = logId;
        this.setSqlInfoId(sqlInfoId);
        this.executeParam = executeParam;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getSqlInfoId() {
        return this.sqlInfoId;
    }

    public void setSqlInfoId(String sqlInfoId) {
        this.sqlInfoId = sqlInfoId;
    }

    public String getExecuteParam() {
        return this.executeParam;
    }

    public void setExecuteParam(String executeParam) {
        this.executeParam = executeParam;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}

