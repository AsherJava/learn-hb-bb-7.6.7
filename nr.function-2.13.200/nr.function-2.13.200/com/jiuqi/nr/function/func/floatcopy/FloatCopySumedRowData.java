/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.nr.function.func.floatcopy;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyRowData;

public class FloatCopySumedRowData
extends FloatCopyRowData {
    private StatUnit[] sumDatas;

    public FloatCopySumedRowData(FloatCopyRowData rowData) {
        super(rowData.size());
        this.rowKey = rowData.rowKey;
        this.keyColumnValue = rowData.keyColumnValue;
        this.sumDatas = new StatUnit[rowData.size()];
        for (int i = 0; i < rowData.size(); ++i) {
            AbstractData value = rowData.getValue(i);
            this.sumDatas[i] = StatItem.createStatUnit((int)value.dataType);
            this.setValue(i, value);
        }
    }

    @Override
    public void setValue(int index, AbstractData value) {
        this.sumDatas[index].statistic(value);
        super.setValue(index, this.sumDatas[index].getResult());
    }
}

