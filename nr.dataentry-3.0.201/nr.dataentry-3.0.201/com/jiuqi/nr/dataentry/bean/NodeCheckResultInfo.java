/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NodeCheckResultInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int totalCheckUnit;
    private int unPassUnit;
    private Map<String, List<NodeCheckResultItem>> nodeCheckResult;
    private List<Map<String, DimensionValue>> dimensionList = new ArrayList<Map<String, DimensionValue>>();

    public List<Map<String, DimensionValue>> getDimensionList() {
        return this.dimensionList;
    }

    public void setDimensionList(List<Map<String, DimensionValue>> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public int getTotalCheckUnit() {
        return this.totalCheckUnit;
    }

    public void setTotalCheckUnit(int totalCheckUnit) {
        this.totalCheckUnit = totalCheckUnit;
    }

    public int getUnPassUnit() {
        return this.unPassUnit;
    }

    public void setUnPassUnit(int unPassUnit) {
        this.unPassUnit = unPassUnit;
    }

    public Map<String, List<NodeCheckResultItem>> getNodeCheckResult() {
        return this.nodeCheckResult;
    }

    public void setNodeCheckResult(Map<String, List<NodeCheckResultItem>> nodeCheckResult) {
        this.nodeCheckResult = nodeCheckResult;
    }
}

