/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.Map;

public class IndexItem {
    private Map<String, Integer> currentIndexes;
    private int totalCount;

    public IndexItem(Map<String, Integer> currentIndexes, int totalCount) {
        this.currentIndexes = currentIndexes;
        this.totalCount = totalCount;
    }

    public int getCurrentIndex(DimensionValueSet rowKey) {
        String rowKeyData = rowKey.toString();
        if (this.currentIndexes.containsKey(rowKeyData)) {
            return this.currentIndexes.get(rowKeyData);
        }
        return -1;
    }

    public int getTotalCount() {
        return this.totalCount;
    }
}

