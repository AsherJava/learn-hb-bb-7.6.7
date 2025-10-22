/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

import java.util.List;

public class BatchDelErrorParam {
    private String task;
    private String period;
    private List<String> orgCodes;
    private List<String> modelKeys;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public List<String> getModelKeys() {
        return this.modelKeys;
    }

    public void setModelKeys(List<String> modelKeys) {
        this.modelKeys = modelKeys;
    }
}

