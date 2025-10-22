/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.impl.process.dao;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class UnitState {
    private DimensionValueSet dimensionValueSet;
    private String actionCode;
    private String taskId;
    private boolean isForceUpload;

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public boolean isForceUpload() {
        return this.isForceUpload;
    }

    public void setForceUpload(boolean isForceUpload) {
        this.isForceUpload = isForceUpload;
    }
}

