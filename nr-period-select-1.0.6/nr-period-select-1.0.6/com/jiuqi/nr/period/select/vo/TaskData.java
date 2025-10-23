/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.vo;

public class TaskData {
    private String startData;
    private long startTime;
    private String endData;
    private long endTime;

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getStartData() {
        return this.startData;
    }

    public void setStartData(String startData) {
        this.startData = startData;
    }

    public String getEndData() {
        return this.endData;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }
}

