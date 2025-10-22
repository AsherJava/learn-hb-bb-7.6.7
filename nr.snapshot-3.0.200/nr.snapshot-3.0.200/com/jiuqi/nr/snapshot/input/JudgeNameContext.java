/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.snapshot.input;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class JudgeNameContext {
    private String snapshotId;
    private String title;
    private String taskKey;
    private DimensionValueSet dimensionValueSet;

    public JudgeNameContext() {
    }

    public JudgeNameContext(String snapshotId, String title, String taskKey, DimensionValueSet dimensionValueSet) {
        this.snapshotId = snapshotId;
        this.title = title;
        this.taskKey = taskKey;
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getSnapshotId() {
        return this.snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }
}

