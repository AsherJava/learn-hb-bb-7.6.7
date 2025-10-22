/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.snapshot.message.DataRange;

public class ReversionBySnapshotContext {
    private DimensionCombination currentDim;
    private String formSchemeKey;
    private DataRange dataRange;
    private String snapshotId;

    public ReversionBySnapshotContext() {
    }

    public ReversionBySnapshotContext(DimensionCombination currentDim, String formSchemeKey, DataRange dataRange, String snapshotId) {
        this.currentDim = currentDim;
        this.formSchemeKey = formSchemeKey;
        this.dataRange = dataRange;
        this.snapshotId = snapshotId;
    }

    public DimensionCombination getCurrentDim() {
        return this.currentDim;
    }

    public void setCurrentDim(DimensionCombination currentDim) {
        this.currentDim = currentDim;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DataRange getDataRange() {
        return this.dataRange;
    }

    public void setDataRange(DataRange dataRange) {
        this.dataRange = dataRange;
    }

    public String getSnapshotId() {
        return this.snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }
}

