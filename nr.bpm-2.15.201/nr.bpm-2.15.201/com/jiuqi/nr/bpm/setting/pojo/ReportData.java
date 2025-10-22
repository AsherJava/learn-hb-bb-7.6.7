/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.pojo;

import java.util.Date;
import java.util.List;

public class ReportData {
    private String id;
    private String title;
    private List<ReportData> children;
    private int startStatus;
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

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public List<ReportData> getChildren() {
        return this.children;
    }

    public void setChildren(List<ReportData> children) {
        this.children = children;
    }

    public int getStartStatus() {
        return this.startStatus;
    }

    public void setStartStatus(int startStatus) {
        this.startStatus = startStatus;
    }
}

