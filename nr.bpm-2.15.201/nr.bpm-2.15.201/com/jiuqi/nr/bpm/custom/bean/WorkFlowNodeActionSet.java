/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 */
package com.jiuqi.nr.bpm.custom.bean;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.nr.bpm.custom.serializer.WorkFlowNodeActionSetDeserializer;
import java.util.HashMap;
import java.util.Map;

@JsonDeserialize(using=WorkFlowNodeActionSetDeserializer.class)
public class WorkFlowNodeActionSet {
    private boolean needbuildVersion = false;
    private boolean needOptDesc = false;
    private boolean needAutoCalculate = false;
    private boolean needAutoCheck = false;
    private int error_passType = 0;
    private int warnning_passType = 0;
    private int info_passType = 0;
    private String ignoreStatus;
    private String optDescStatus;
    private Map<String, String> exset = new HashMap<String, String>();

    public Map<String, String> getExset() {
        return this.exset;
    }

    public void setExset(Map<String, String> exset) {
        this.exset = exset;
    }

    public boolean isNeedOptDesc() {
        return this.needOptDesc;
    }

    public void setNeedOptDesc(boolean needOptDesc) {
        this.needOptDesc = needOptDesc;
    }

    public boolean isNeedAutoCalculate() {
        return this.needAutoCalculate;
    }

    public void setNeedAutoCalculate(boolean needAutoCalculate) {
        this.needAutoCalculate = needAutoCalculate;
    }

    public boolean isNeedAutoCheck() {
        return this.needAutoCheck;
    }

    public void setNeedAutoCheck(boolean needAutoCheck) {
        this.needAutoCheck = needAutoCheck;
    }

    public int getError_passType() {
        return this.error_passType;
    }

    public void setError_passType(int error_passType) {
        this.error_passType = error_passType;
    }

    public int getWarnning_passType() {
        return this.warnning_passType;
    }

    public void setWarnning_passType(int warnning_passType) {
        this.warnning_passType = warnning_passType;
    }

    public int getInfo_passType() {
        return this.info_passType;
    }

    public void setInfo_passType(int info_passType) {
        this.info_passType = info_passType;
    }

    public String getIgnoreStatus() {
        return this.ignoreStatus;
    }

    public void setIgnoreStatus(String ignoreStatus) {
        this.ignoreStatus = ignoreStatus;
    }

    public String getOptDescStatus() {
        return this.optDescStatus;
    }

    public void setOptDescStatus(String optDescStatus) {
        this.optDescStatus = optDescStatus;
    }

    public boolean isNeedbuildVersion() {
        return this.needbuildVersion;
    }

    public void setNeedbuildVersion(boolean needbuildVersion) {
        this.needbuildVersion = needbuildVersion;
    }
}

