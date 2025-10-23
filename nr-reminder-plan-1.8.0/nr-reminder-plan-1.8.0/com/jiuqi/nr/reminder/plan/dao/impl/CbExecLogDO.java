/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.reminder.plan.dao.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.springframework.jdbc.core.RowMapper;

public class CbExecLogDO
implements RowMapper<CbExecLogDO> {
    private String logId;
    private String planId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp startTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Timestamp endTime;
    private int status;
    private String execUser;
    private String message;
    public static final String NR_CB_EXEC_LOG = "NR_CB_EXEC_LOG";
    public static final String ID_COLUMN = "LOG_ID";
    public static final String PLAN_ID_COLUMN = "P_ID";
    public static final String START_TIME_COLUMN = "LOG_START_TIME";
    public static final String END_TIME_COLUMN = "LOG_END_TIME";
    public static final String STATUS_COLUMN = "P_STATUS";
    public static final String EXEC_USER_COLUMN = "LOG_EXEC_USER";
    public static final String MESSAGE_COLUMN = "LOG_MESSAGE";

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getPlanId() {
        return this.planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
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

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExecUser() {
        return this.execUser;
    }

    public void setExecUser(String execUser) {
        this.execUser = execUser;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CbExecLogDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CbExecLogDO execLogDO = new CbExecLogDO();
        execLogDO.setLogId(rs.getString(ID_COLUMN));
        execLogDO.setPlanId(rs.getString(PLAN_ID_COLUMN));
        execLogDO.setStartTime(rs.getTimestamp(START_TIME_COLUMN));
        execLogDO.setEndTime(rs.getTimestamp(END_TIME_COLUMN));
        execLogDO.setStatus(rs.getInt(STATUS_COLUMN));
        execLogDO.setExecUser(rs.getString(EXEC_USER_COLUMN));
        execLogDO.setMessage(rs.getString(MESSAGE_COLUMN));
        return execLogDO;
    }
}

