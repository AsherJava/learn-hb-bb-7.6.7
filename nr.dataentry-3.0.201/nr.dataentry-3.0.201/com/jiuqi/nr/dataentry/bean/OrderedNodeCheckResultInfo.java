/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultItem;
import java.util.List;

public class OrderedNodeCheckResultInfo
extends NodeCheckResultInfo {
    private List<List<NodeCheckResultItem>> orderedNodeCheckResult;

    public List<List<NodeCheckResultItem>> getOrderedNodeCheckResult() {
        return this.orderedNodeCheckResult;
    }

    public void setOrderedNodeCheckResult(List<List<NodeCheckResultItem>> orderedNodeCheckResult) {
        this.orderedNodeCheckResult = orderedNodeCheckResult;
    }
}

