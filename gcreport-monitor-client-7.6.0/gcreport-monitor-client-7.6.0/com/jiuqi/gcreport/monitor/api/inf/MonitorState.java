/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.inf;

public class MonitorState {
    private int value;
    private String title;
    private String percent;
    private String combTitle;

    public MonitorState() {
    }

    public MonitorState(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCombTitle() {
        return this.combTitle;
    }

    public void setCombTitle(String combTitle) {
        this.combTitle = combTitle;
    }

    public String getPercent() {
        return this.percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}

