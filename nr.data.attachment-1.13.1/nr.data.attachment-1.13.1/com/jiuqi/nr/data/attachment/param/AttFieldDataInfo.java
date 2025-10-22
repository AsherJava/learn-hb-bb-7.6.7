/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.attachment.param;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public class AttFieldDataInfo {
    private String groupKey;
    private DimensionCombination dimCombination;
    private String fieldKey;

    public AttFieldDataInfo(String groupKey, DimensionCombination dimCombination, String fieldKey) {
        this.groupKey = groupKey;
        this.dimCombination = dimCombination;
        this.fieldKey = fieldKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public DimensionCombination getDimCombination() {
        return this.dimCombination;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }
}

