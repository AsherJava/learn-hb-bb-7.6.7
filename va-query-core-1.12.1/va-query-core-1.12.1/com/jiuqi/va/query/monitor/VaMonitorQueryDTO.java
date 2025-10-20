/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.monitor;

import java.util.Date;

public class VaMonitorQueryDTO {
    private String id;
    private String hostName;
    private String code;
    private String bizName;
    private String args;
    private Date mPeriod;
    private Integer executeCount;
    private Long maxTime;
    private Long minTime;
    private Long avgTime;
    private Long totalTime;
    private Integer duration;
    private Date createTime;
    private Date eventTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
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

    public String getArgs() {
        return this.args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public Date getmPeriod() {
        return this.mPeriod;
    }

    public void setmPeriod(Date mPeriod) {
        this.mPeriod = mPeriod;
    }

    public Integer getExecuteCount() {
        return this.executeCount;
    }

    public void setExecuteCount(Integer executeCount) {
        this.executeCount = executeCount;
    }

    public Long getMaxTime() {
        return this.maxTime;
    }

    public void setMaxTime(Long maxTime) {
        this.maxTime = maxTime;
    }

    public Long getMinTime() {
        return this.minTime;
    }

    public void setMinTime(Long minTime) {
        this.minTime = minTime;
    }

    public Long getAvgTime() {
        return this.avgTime;
    }

    public void setAvgTime(Long avgTime) {
        this.avgTime = avgTime;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEventTime() {
        return this.eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Long getTotalTime() {
        return this.totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public String toString() {
        return "VaMonitorQueryDTO{id='" + this.id + '\'' + ", hostName='" + this.hostName + '\'' + ", code='" + this.code + '\'' + ", bizName='" + this.bizName + '\'' + ", args='" + this.args + '\'' + ", mPeriod=" + this.mPeriod + ", executeCount=" + this.executeCount + ", maxTime=" + this.maxTime + ", minTime=" + this.minTime + ", avgTime=" + this.avgTime + ", totalTime=" + this.totalTime + ", duration=" + this.duration + ", createTime=" + this.createTime + ", eventTime=" + this.eventTime + '}';
    }
}

