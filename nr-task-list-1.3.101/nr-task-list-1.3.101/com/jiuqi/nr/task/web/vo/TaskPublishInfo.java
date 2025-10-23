/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import java.util.Date;

public class TaskPublishInfo {
    private String progressId;
    private String taskId;
    private String userId;
    private Date operTime;
    private int operType;
    private int operStatus;
    private String info;
    private int needShow;
    private String stackinfos;

    public String getProgressId() {
        return this.progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getOperTime() {
        return this.operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public int getOperType() {
        return this.operType;
    }

    public void setOperType(int operType) {
        this.operType = operType;
    }

    public int getOperStatus() {
        return this.operStatus;
    }

    public void setOperStatus(int operStatus) {
        this.operStatus = operStatus;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getNeedShow() {
        return this.needShow;
    }

    public void setNeedShow(int needShow) {
        this.needShow = needShow;
    }

    public String getStackinfos() {
        return this.stackinfos;
    }

    public void setStackinfos(String stackinfos) {
        this.stackinfos = stackinfos;
    }
}

