/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.nodecheck.bean;

import com.jiuqi.nr.datacheck.nodecheck.bean.UnitCheckResultItem;
import java.io.Serializable;
import java.util.List;

public class NodeCheckResultVO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int totalCheckUnit;
    private int unPassUnit;
    private List<UnitCheckResultItem> resultItems;

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

    public List<UnitCheckResultItem> getResultItems() {
        return this.resultItems;
    }

    public void setResultItems(List<UnitCheckResultItem> resultItems) {
        this.resultItems = resultItems;
    }
}

