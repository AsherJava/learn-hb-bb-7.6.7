/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatUnit;

class FloatSumUnit
implements StatUnit {
    private double total;
    private AbstractData result = FloatData.NULL;
    private boolean isNull = true;

    FloatSumUnit() {
    }

    @Override
    public int getStatKind() {
        return 1;
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
            this.result = new FloatData(this.total);
        }
        return this.result;
    }

    @Override
    public void reset() {
        this.total = 0.0;
        this.result = FloatData.NULL;
        this.isNull = true;
    }

    @Override
    public void statistic(AbstractData value) {
        try {
            if (!value.isNull) {
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

