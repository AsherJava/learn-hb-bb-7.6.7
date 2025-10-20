/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.monitor;

import java.util.Date;

public class QueryLogEvent {
    private String host;
    private String code;
    private String bizName;
    private Object[] args;
    private long duration;
    private long startTime;
    private Date eventTime;

    public QueryLogEvent() {
    }

    public QueryLogEvent(String code, String bizName, Object[] args) {
        this.code = code;
        this.bizName = bizName;
        this.args = args;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBizName() {
        return this.bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getEventTime() {
        return this.eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}

