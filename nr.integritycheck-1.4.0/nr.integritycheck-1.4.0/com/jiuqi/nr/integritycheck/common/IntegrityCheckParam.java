/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.integritycheck.common;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.ArrayList;
import java.util.List;

public class IntegrityCheckParam {
    private String batchId;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCollection dims;
    private List<String> formKeys = new ArrayList<String>();

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCollection getDims() {
        return this.dims;
    }

    public void setDims(DimensionCollection dims) {
        this.dims = dims;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }
}

