/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datastatus.facade.obj;

import java.util.List;

public class RefreshStatusPar {
    private String taskKey;
    private List<String> periods;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<String> getPeriods() {
        return this.periods;
    }

    public void setPeriods(List<String> periods) {
        this.periods = periods;
    }
}

