/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.efdc.extract.impl.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class DataFetchEnv {
    private String instance;
    private String unitCode;
    private String startTime;
    private String endTime;
    private boolean isIncludeUncharged = false;
    private String unitName;
    private String periodScheme;
    private boolean isReturnExpression = false;
    private String taskID;
    private Map<String, String> otherEntity;
    private boolean stopOnSyntaxErr = true;
    private String startAdjustPeriod;
    private String endAdjustPeriod;
    private boolean includeAdjustPeriod;
    private String bblx;

    public String getInstance() {
        return this.instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    @JsonProperty(value="isReturnExpression")
    public boolean isReturnExpression() {
        return this.isReturnExpression;
    }

    public void setReturnExpression(boolean isReturnExpression) {
        this.isReturnExpression = isReturnExpression;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @JsonProperty(value="isIncludeUncharged")
    public boolean isIncludeUncharged() {
        return this.isIncludeUncharged;
    }

    public void setIncludUncharged(boolean isIncludeUncharged) {
        this.isIncludeUncharged = isIncludeUncharged;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setStopOnSyntaxErr(boolean stopOnSyntaxErr) {
        this.stopOnSyntaxErr = stopOnSyntaxErr;
    }

    public boolean isStopOnSyntaxErr() {
        return this.stopOnSyntaxErr;
    }

    public void setEndAdjustPeriod(String endAdjustPeriod) {
        this.endAdjustPeriod = endAdjustPeriod;
    }

    public String getEndAdjustPeriod() {
        return this.endAdjustPeriod;
    }

    public void setStartAdjustPeriod(String startAdjustPeriod) {
        this.startAdjustPeriod = startAdjustPeriod;
    }

    public String getStartAdjustPeriod() {
        return this.startAdjustPeriod;
    }

    public void setPeriodScheme(String periodScheme) {
        this.periodScheme = periodScheme;
    }

    public String getPeriodScheme() {
        return this.periodScheme;
    }

    public Map<String, String> getOtherEntity() {
        return this.otherEntity;
    }

    public void setOtherEntity(Map<String, String> otherEntity) {
        this.otherEntity = otherEntity;
    }

    public String getTaskID() {
        return this.taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public boolean isIncludeAdjustPeriod() {
        return this.includeAdjustPeriod;
    }

    public void setIncludeAdjustPeriod(boolean includeAdjustPeriod) {
        this.includeAdjustPeriod = includeAdjustPeriod;
    }

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }
}

