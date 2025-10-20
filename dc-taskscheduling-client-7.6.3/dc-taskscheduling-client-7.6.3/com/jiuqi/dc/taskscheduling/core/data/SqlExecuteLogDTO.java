/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.dc.taskscheduling.core.data;

import com.jiuqi.common.base.util.UUIDUtils;
import java.util.Date;

public class SqlExecuteLogDTO {
    private String id;
    private String logId;
    private String sqlString;
    private String executeParam;
    private Date startTime;

    public SqlExecuteLogDTO() {
        this.id = UUIDUtils.newHalfGUIDStr();
    }

    public SqlExecuteLogDTO(String id, String logId, String sqlString, String executeParam, Date startTime) {
        this.id = id;
        this.logId = logId;
        this.sqlString = sqlString;
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

    public String getSqlString() {
        return this.sqlString;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
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

