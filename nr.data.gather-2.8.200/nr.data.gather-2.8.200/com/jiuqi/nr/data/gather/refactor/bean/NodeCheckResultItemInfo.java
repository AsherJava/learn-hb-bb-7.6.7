/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.data.gather.refactor.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.gather.bean.NodeCheckResultItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NodeCheckResultItemInfo {
    private String unitTitle;
    private Map<String, DimensionValue> dimensionValue;
    private List<NodeCheckResultItem> nodeCheckResultItems = new ArrayList<NodeCheckResultItem>();

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public List<NodeCheckResultItem> getNodeCheckResultItems() {
        return this.nodeCheckResultItems;
    }

    public void setNodeCheckResultItems(List<NodeCheckResultItem> nodeCheckResultItems) {
        this.nodeCheckResultItems = nodeCheckResultItems;
    }

    public Map<String, DimensionValue> getDimensionValue() {
        return this.dimensionValue;
    }

    public void setDimensionValue(Map<String, DimensionValue> dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NodeCheckResultItemInfo)) {
            return false;
        }
        NodeCheckResultItemInfo other = (NodeCheckResultItemInfo)obj;
        return other.getUnitTitle().equals(this.unitTitle);
    }
}

