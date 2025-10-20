/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.dc.base.common.jdbc.domain;

import com.jiuqi.common.base.util.UUIDUtils;
import java.util.Date;

public class SqlExecuteLogDO {
    private String id;
    private String logId;
    private String sqlInfoId;
    private String executeParam;
    private Date startTime;

    public SqlExecuteLogDO() {
        this.id = UUIDUtils.newHalfGUIDStr();
    }

    public SqlExecuteLogDO(String id, String logId, String sqlInfoId, String executeParam, Date startTime) {
        this.id = id;
        this.logId = logId;
        this.setSqlInfoId(sqlInfoId);
        this.executeParam = executeParam;
        this.startTime = startTime;
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

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}

