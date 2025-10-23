/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.domain;

public class MonitorConfigInfo {
    private int checkInterval;
    private int leakDetectionThreshold;

    public MonitorConfigInfo() {
    }

    public MonitorConfigInfo(int checkInterval, int leakDetectionThreshold) {
        this.checkInterval = checkInterval;
        this.leakDetectionThreshold = leakDetectionThreshold;
    }

    public int getCheckInterval() {
        return this.checkInterval;
    }

    public void setCheckInterval(int checkInterval) {
        this.checkInterval = checkInterval;
    }

    public int getLeakDetectionThreshold() {
        return this.leakDetectionThreshold;
    }

    public void setLeakDetectionThreshold(int leakDetectionThreshold) {
        this.leakDetectionThreshold = leakDetectionThreshold;
    }
}

