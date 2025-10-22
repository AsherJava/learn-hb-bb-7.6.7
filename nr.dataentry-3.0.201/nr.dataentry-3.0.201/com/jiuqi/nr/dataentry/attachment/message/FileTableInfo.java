/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataentry.attachment.message;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.Set;

public class FileTableInfo {
    private DimensionValueSet dimensionValueSet;
    private String fieldKey;
    private String groupKey;
    private Set<String> fileKeys;

    public FileTableInfo(DimensionValueSet dimensionValueSet, String fieldKey, String groupKey, Set<String> fileKeys) {
        this.dimensionValueSet = dimensionValueSet;
        this.fieldKey = fieldKey;
        this.groupKey = groupKey;
        this.fileKeys = fileKeys;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public Set<String> getFileKeys() {
        return this.fileKeys;
    }

    public void setFileKeys(Set<String> fileKeys) {
        this.fileKeys = fileKeys;
    }
}

