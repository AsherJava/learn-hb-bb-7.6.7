/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.DataTypes
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.nr.data.engine.analysis.parse.func;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.executors.StatUnit;
import java.util.ArrayList;
import java.util.List;

public class VarianceUnit
implements StatUnit {
    private StatUnit countUnit = StatItem.createStatUnit((int)2, (int)3);
    private StatUnit sumUnit = StatItem.createStatUnit((int)1, (int)3);
    private List<AbstractData> values = new ArrayList<AbstractData>();
    private static final AbstractData CONST1 = AbstractData.valueOf((int)1);
    private AbstractData result = null;

    public int getStatKind() {
        return -1;
    }

    public int getResultType() {
        return 3;
    }

    public AbstractData getResult() {
        if (this.result == null) {
            this.result = DataTypes.getNullValue((int)this.getResultType());
        }
        return this.result;
    }

    public void reset() {
        this.countUnit.reset();
        this.sumUnit.reset();
        this.values.clear();
        this.result = null;
    }

    public void statistic(AbstractData value) {
        this.countUnit.statistic(CONST1);
        this.sumUnit.statistic(value);
        this.values.add(value);
        int count = this.countUnit.getResult().getAsInt();
        double sum = this.sumUnit.getResult().isNull ? 0.0 : this.sumUnit.getResult().getAsFloat();
        double avg = sum / (double)count;
        double varianceSum = 0.0;
        for (AbstractData v : this.values) {
            double d = v.isNull ? 0.0 : v.getAsFloat();
            varianceSum += Math.sqrt(d - avg);
        }
        double variance = varianceSum / (double)count;
        this.result = AbstractData.valueOf((double)variance);
    }
}

