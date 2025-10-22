/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.attachment.message;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.Set;

public class FileRelInfo {
    private DimensionValueSet dimensionValueSet;
    private String groupKey;
    private String fieldKey;
    private Set<String> fileKeys;

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public Set<String> getFileKeys() {
        return this.fileKeys;
    }

    public void setFileKeys(Set<String> fileKeys) {
        this.fileKeys = fileKeys;
    }
}

