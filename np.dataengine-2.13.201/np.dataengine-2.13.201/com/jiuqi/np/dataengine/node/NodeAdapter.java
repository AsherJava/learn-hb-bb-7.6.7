/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodModifier
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.np.dataengine.common.DataLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.period.PeriodModifier;

public final class NodeAdapter {
    private final DynamicDataNode nodeInfo;
    private AbstractData value;
    private CellDataNode cellInfo;

    public NodeAdapter(DynamicDataNode nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    public NodeAdapter(DynamicDataNode nodeInfo, AbstractData value) {
        this(nodeInfo);
        this.value = value;
    }

    public DataLinkColumn getDataLinkInfo() {
        return this.nodeInfo.getDataLink();
    }

    public AbstractData getValue() {
        return this.value;
    }

    public DimensionValueSet getDimensionRestriction() {
        return null;
    }

    public PeriodModifier getPeriodModifier() {
        return null;
    }

    public DynamicDataNode getNodeInfo() {
        return this.nodeInfo;
    }

    public CellDataNode getCellInfo() {
        return this.cellInfo;
    }

    public void setCellInfo(CellDataNode cellInfo) {
        this.cellInfo = cellInfo;
    }
}

