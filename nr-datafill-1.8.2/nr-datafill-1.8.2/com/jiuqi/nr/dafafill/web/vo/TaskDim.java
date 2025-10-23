/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dafafill.web.vo;

import com.jiuqi.nr.dafafill.model.QueryField;

public class TaskDim {
    private String taskCode;
    private QueryField unit;
    private int version;
    private QueryField period;

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public QueryField getUnit() {
        return this.unit;
    }

    public void setUnit(QueryField unit) {
        this.unit = unit;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public QueryField getPeriod() {
        return this.period;
    }

    public void setPeriod(QueryField period) {
        this.period = period;
    }
}

