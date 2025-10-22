/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.data.IntData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatUnit;

class IntSumUnit
implements StatUnit {
    private int total;
    private AbstractData result = IntData.NULL;
    private boolean isNull = true;

    IntSumUnit() {
    }

    @Override
    public int getStatKind() {
        return 1;
    }

    @Override
    public int getResultType() {
        return 4;
    }

    @Override
    public AbstractData getResult() {
        if (this.isNull) {
            return DataTypes.getNullValue(this.getResultType());
        }
        if (this.result == null) {
            this.result = new IntData(this.total);
        }
        return this.result;
    }

    @Override
    public void reset() {
        this.total = 0;
        this.result = IntData.NULL;
        this.isNull = true;
    }

    @Override
    public void statistic(AbstractData value) {
        try {
            if (!value.isNull) {
                this.total += value.getAsInt();
                this.result = null;
            }
            this.isNull = false;
        }
        catch (DataTypeException dataTypeException) {
            // empty catch block
        }
    }
}

