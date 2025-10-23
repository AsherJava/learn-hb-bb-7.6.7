/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.instance.entity;

import java.util.Calendar;
import java.util.List;

public class StatusData {
    private int startStatus;
    private Calendar startTime;
    private List<StatusData> statusData;

    public Calendar getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public int getStartStatus() {
        return this.startStatus;
    }

    public void setStartStatus(int startStatus) {
        this.startStatus = startStatus;
    }

    public List<StatusData> getStatusData() {
        return this.statusData;
    }

    public void setStatusData(List<StatusData> statusData) {
        this.statusData = statusData;
    }
}

