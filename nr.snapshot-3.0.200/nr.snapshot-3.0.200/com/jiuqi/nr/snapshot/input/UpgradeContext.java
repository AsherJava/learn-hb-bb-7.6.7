/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.input;

import java.io.Serializable;

public class UpgradeContext
implements Serializable {
    private static final long serialVersionUID = 3892336509099197768L;
    private String taskKey;
    private String fromPeriod;
    private String toPeriod;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }
}

