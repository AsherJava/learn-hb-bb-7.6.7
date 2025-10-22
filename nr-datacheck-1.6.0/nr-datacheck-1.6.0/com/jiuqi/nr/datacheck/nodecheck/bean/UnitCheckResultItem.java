/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.gather.bean.NodeCheckResultItem
 */
package com.jiuqi.nr.datacheck.nodecheck.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.gather.bean.NodeCheckResultItem;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;

public class UnitCheckResultItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String unitKey;
    private String unitTitle;
    private Map<String, String> dimValue;
    private LinkedList<Map<String, DimensionValue>> dimensions;
    private LinkedList<NodeCheckResultItem> resultItems;

    public String getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public LinkedList<Map<String, DimensionValue>> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(LinkedList<Map<String, DimensionValue>> dimensions) {
        this.dimensions = dimensions;
    }

    public LinkedList<NodeCheckResultItem> getResultItems() {
        return this.resultItems;
    }

    public void setResultItems(LinkedList<NodeCheckResultItem> resultItems) {
        this.resultItems = resultItems;
    }

    public Map<String, String> getDimValue() {
        return this.dimValue;
    }

    public void setDimValue(Map<String, String> dimValue) {
        this.dimValue = dimValue;
    }
}

