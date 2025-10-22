/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.data.engine.gather.CheckErrorItem;
import java.util.List;

public class NodeCheckResult {
    private List<CheckErrorItem> errorItems;
    private int checkedNodeCount;
    private int unpassedNodeCount;

    public List<CheckErrorItem> getErrorItems() {
        return this.errorItems;
    }

    public void setErrorItems(List<CheckErrorItem> errorItems) {
        this.errorItems = errorItems;
    }

    public int getCheckedNodeCount() {
        return this.checkedNodeCount;
    }

    public void setCheckedNodeCount(int checkedNodeCount) {
        this.checkedNodeCount = checkedNodeCount;
    }

    public int getUnpassedNodeCount() {
        return this.unpassedNodeCount;
    }

    public void setUnpassedNodeCount(int unpassedNodeCount) {
        this.unpassedNodeCount = unpassedNodeCount;
    }
}

