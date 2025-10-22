/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatUnit;

class FloatAvgUnit
implements StatUnit {
    private int count;
    private double total;
    private AbstractData result = null;
    private boolean isNull = true;

    FloatAvgUnit() {
    }

    @Override
    public int getStatKind() {
        return 3;
    }

    @Override
    public int getResultType() {
        return 3;
    }

    @Override
    public AbstractData getResult() {
        if (this.isNull) {
            return DataTypes.getNullValue(this.getResultType());
        }
        if (this.result == null) {
            this.result = this.count > 0 ? new FloatData(this.total / (double)this.count) : FloatData.NULL;
        }
        return this.result;
    }

    @Override
    public void reset() {
        this.count = 0;
        this.total = 0.0;
        this.result = null;
        this.isNull = true;
    }

    @Override
    public void statistic(AbstractData value) {
        try {
            if (!value.isNull) {
                ++this.count;
                this.total += value.getAsFloat();
                this.result = null;
            }
            this.isNull = false;
        }
        catch (DataTypeException dataTypeException) {
            // empty catch block
        }
    }
}

