/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.gcreport.inputdata.dataentryext.syncformlock.params;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GcBatchFormLockParam {
    private String taskKey;
    private String formSchemeKey;
    private Set<String> formKeys;
    private List<Map<String, DimensionValue>> dimensionValueMaps;
    private Map<String, DimensionValue> dimensionValueMap;
    private boolean isLock;
    private boolean forceUnLock = false;

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

    public Set<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(Set<String> formKeys) {
        this.formKeys = formKeys;
    }

    public List<Map<String, DimensionValue>> getDimensionValueMaps() {
        return this.dimensionValueMaps;
    }

    public void setDimensionValueMaps(List<Map<String, DimensionValue>> dimensionValueMaps) {
        this.dimensionValueMaps = dimensionValueMaps;
    }

    public Map<String, DimensionValue> getDimensionValueMap() {
        return this.dimensionValueMap;
    }

    public void setDimensionValueMap(Map<String, DimensionValue> dimensionValueMap) {
        this.dimensionValueMap = dimensionValueMap;
    }

    public boolean isLock() {
        return this.isLock;
    }

    public void setLock(boolean lock) {
        this.isLock = lock;
    }

    public boolean isForceUnLock() {
        return this.forceUnLock;
    }

    public void setForceUnLock(boolean forceUnLock) {
        this.forceUnLock = forceUnLock;
    }
}

