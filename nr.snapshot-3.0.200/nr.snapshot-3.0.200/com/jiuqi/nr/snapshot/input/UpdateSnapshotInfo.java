/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public class UpdateSnapshotInfo {
    private String snapshotId;
    private String title;
    private String describe;
    private String taskKey;
    private DimensionCombination dimensionCombination;

    public UpdateSnapshotInfo() {
    }

    public UpdateSnapshotInfo(String snapshotId, String title, String describe, String taskKey, DimensionCombination dimensionCombination) {
        this.snapshotId = snapshotId;
        this.title = title;
        this.describe = describe;
        this.taskKey = taskKey;
        this.dimensionCombination = dimensionCombination;
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

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }
}

