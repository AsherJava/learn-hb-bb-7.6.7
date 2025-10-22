/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.gather.refactor.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.gather.refactor.bean.NodeCheckResultItemInfo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class NodeCheckResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int totalCheckUnit;
    private int unPassUnit;
    private String UnitKey;
    private DimensionValueSet dimensionValueSet;
    private boolean isLeafUnit = false;
    private Set<NodeCheckResultItemInfo> resultItemInfos = new HashSet<NodeCheckResultItemInfo>();

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

    public String getUnitKey() {
        return this.UnitKey;
    }

    public void setUnitKey(String unitKey) {
        this.UnitKey = unitKey;
    }

    public boolean isLeafUnit() {
        return this.isLeafUnit;
    }

    public void setLeafUnit(boolean leafUnit) {
        this.isLeafUnit = leafUnit;
    }

    public Set<NodeCheckResultItemInfo> getResultItemInfos() {
        return this.resultItemInfos;
    }

    public void setResultItemInfos(Set<NodeCheckResultItemInfo> resultItemInfos) {
        this.resultItemInfos = resultItemInfos;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }
}

