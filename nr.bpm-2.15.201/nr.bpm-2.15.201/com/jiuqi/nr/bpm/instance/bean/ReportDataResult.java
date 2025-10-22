/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.bpm.instance.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;

public class ReportDataResult {
    private String id;
    private String title;
    private List<ReportDataResult> children;
    private int startStatus;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date startTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ReportDataResult> getChildren() {
        return this.children;
    }

    public void setChildren(List<ReportDataResult> children) {
        this.children = children;
    }

    public int getStartStatus() {
        return this.startStatus;
    }

    public void setStartStatus(int startStatus) {
        this.startStatus = startStatus;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}

