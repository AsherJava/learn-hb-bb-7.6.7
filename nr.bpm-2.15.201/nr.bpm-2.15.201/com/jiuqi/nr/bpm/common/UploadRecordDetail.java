/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

public class UploadRecordDetail {
    private String unitKey;
    private String unit;
    private String state;
    private int uploadCount;
    private int rejectCount;
    private String initialUpdateTime;
    private String initialUpdateProcessor;
    private String initialRejectTime;
    private String initialRejectProcessor;
    private String lastUpdateTime;
    private String lastUpdateProcessor;
    private String lastRejectTime;
    private String lastRejectProcessor;

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getInitialUpdateTime() {
        return this.initialUpdateTime;
    }

    public void setInitialUpdateTime(String initialUpdateTime) {
        this.initialUpdateTime = initialUpdateTime;
    }

    public String getInitialUpdateProcessor() {
        return this.initialUpdateProcessor;
    }

    public void setInitialUpdateProcessor(String initialUpdateProcessor) {
        this.initialUpdateProcessor = initialUpdateProcessor;
    }

    public String getInitialRejectTime() {
        return this.initialRejectTime;
    }

    public void setInitialRejectTime(String initialRejectTime) {
        this.initialRejectTime = initialRejectTime;
    }

    public String getInitialRejectProcessor() {
        return this.initialRejectProcessor;
    }

    public void setInitialRejectProcessor(String initialRejectProcessor) {
        this.initialRejectProcessor = initialRejectProcessor;
    }

    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateProcessor() {
        return this.lastUpdateProcessor;
    }

    public void setLastUpdateProcessor(String lastUpdateProcessor) {
        this.lastUpdateProcessor = lastUpdateProcessor;
    }

    public String getLastRejectTime() {
        return this.lastRejectTime;
    }

    public void setLastRejectTime(String lastRejectTime) {
        this.lastRejectTime = lastRejectTime;
    }

    public String getLastRejectProcessor() {
        return this.lastRejectProcessor;
    }

    public void setLastRejectProcessor(String lastRejectProcessor) {
        this.lastRejectProcessor = lastRejectProcessor;
    }

    public int getUploadCount() {
        return this.uploadCount;
    }

    public void incrementUploadCount() {
        this.uploadCount = this.getUploadCount() + 1;
    }

    public void setUploadCount(int uploadCount) {
        this.uploadCount = uploadCount;
    }

    public int getRejectCount() {
        return this.rejectCount;
    }

    public void incrementRejectCount() {
        this.rejectCount = this.getRejectCount() + 1;
    }

    public void setRejectCount(int rejectCount) {
        this.rejectCount = rejectCount;
    }
}

