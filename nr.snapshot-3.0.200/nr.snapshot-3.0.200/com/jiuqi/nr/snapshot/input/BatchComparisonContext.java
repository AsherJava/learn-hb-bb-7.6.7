/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.snapshot.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;
import java.util.Map;

public class BatchComparisonContext {
    private DimensionCollection currentDims;
    private String formSchemeKey;
    private List<String> formKeys;
    private Map<String, String> dwSnapshotId;

    public BatchComparisonContext() {
    }

    public BatchComparisonContext(DimensionCollection currentDims, String formSchemeKey, List<String> formKeys, Map<String, String> dwSnapshotId) {
        this.currentDims = currentDims;
        this.formSchemeKey = formSchemeKey;
        this.formKeys = formKeys;
        this.dwSnapshotId = dwSnapshotId;
    }

    public DimensionCollection getCurrentDims() {
        return this.currentDims;
    }

    public void setCurrentDims(DimensionCollection currentDims) {
        this.currentDims = currentDims;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public Map<String, String> getDwSnapshotId() {
        return this.dwSnapshotId;
    }

    public void setDwSnapshotId(Map<String, String> dwSnapshotId) {
        this.dwSnapshotId = dwSnapshotId;
    }
}

