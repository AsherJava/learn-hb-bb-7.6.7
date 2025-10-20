/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import java.util.Date;

public class ProgressInfos {
    private Date operTime;
    private int operType;
    private int operStatus;
    private String info;
    private String stackInfos;
    private int needShow;

    public ProgressInfos() {
        this.setOperType(0);
        this.setOperStatus(0);
        this.setInfo("");
        this.setNeedShow(0);
        this.setOperTime(new Date());
        this.setStackInfos("");
    }

    public String getStackInfos() {
        return this.stackInfos;
    }

    public void setStackInfos(String stackInfos) {
        this.stackInfos = stackInfos;
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
}

