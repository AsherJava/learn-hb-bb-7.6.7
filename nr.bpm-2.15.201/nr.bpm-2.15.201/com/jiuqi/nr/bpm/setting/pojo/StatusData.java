/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.setting.pojo;

import java.util.Date;
import java.util.List;

public class StatusData {
    private int startStatus;
    private Date startTime;
    private List<StatusData> statusData;

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
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

