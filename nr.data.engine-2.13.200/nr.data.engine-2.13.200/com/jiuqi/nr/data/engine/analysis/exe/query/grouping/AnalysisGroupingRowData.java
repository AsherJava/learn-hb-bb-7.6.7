/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.nr.data.engine.analysis.exe.query.grouping;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisRowData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisGroupingRowData
extends AnalysisRowData {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisGroupingRowData.class);
    private StatUnit[] sumDatas;

    public AnalysisGroupingRowData(DimensionValueSet groupKey, AnalysisRowData rowData) {
        super(groupKey, rowData.size(), rowData.getOwnner());
        this.sumDatas = new StatUnit[rowData.size()];
        for (int i = 0; i < this.sumDatas.length; ++i) {
            this.sumDatas[i] = StatItem.createStatUnit((int)this.ownner.getDataTypes()[i]);
        }
    }

    @Override
    public void writeValue(int index, Object value) {
        try {
            AbstractData valueOf = AbstractData.valueOf((Object)value, (int)this.ownner.getDataTypes()[index]);
            this.sumDatas[index].statistic(valueOf);
            super.writeValue(index, this.sumDatas[index].getResult().getAsObject());
        }
        catch (DataTypeException e) {
            logger.error(e.getMessage(), e);
        }
    }
}

