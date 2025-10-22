/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.snapshot.message.DataRange;

public class QuerySnapshotDataSourceContext {
    private String formSchemeKey;
    private DimensionCombination dimensionCombination;
    private DataRange dataRange;
    private String snapshotId;

    public QuerySnapshotDataSourceContext() {
    }

    public QuerySnapshotDataSourceContext(String formSchemeKey, DimensionCombination dimensionCombination, DataRange dataRange, String snapshotId) {
        this.formSchemeKey = formSchemeKey;
        this.dimensionCombination = dimensionCombination;
        this.dataRange = dataRange;
        this.snapshotId = snapshotId;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
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

