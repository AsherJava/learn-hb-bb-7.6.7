/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;

public class StatRow {
    private DimensionValueSet rowKey;
    private StatUnit[] statUnits;

    public StatRow(DimensionValueSet rowKey, int columnCount) {
        this.rowKey = rowKey;
        this.statUnits = new StatUnit[columnCount];
    }

    public DimensionValueSet getRowKey() {
        return this.rowKey;
    }

    public void statistic(AbstractData value, StatItem statItem) {
        StatUnit statUnit = this.getStatUnit(statItem);
        statUnit.statistic(value);
    }

    public AbstractData getResult(StatItem statItem) {
        StatUnit statUnit = this.getStatUnit(statItem);
        return statUnit.getResult();
    }

    private StatUnit getStatUnit(StatItem statItem) {
        StatUnit statUnit = this.statUnits[statItem.getIndex()];
        if (statUnit == null) {
            this.statUnits[statItem.getIndex()] = statUnit = StatItem.createStatUnit(statItem.getStatKind(), statItem.getDataType());
        }
        return statUnit;
    }
}

