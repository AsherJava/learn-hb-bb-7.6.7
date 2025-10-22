/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatUnit;

class MaxStatUnit
implements StatUnit {
    private int dataType;
    private AbstractData result = null;
    private boolean isNull = true;

    public MaxStatUnit(int dataType) {
        this.dataType = dataType;
        this.result = DataTypes.getNullValue(dataType);
    }

    @Override
    public int getStatKind() {
        return 4;
    }

    @Override
    public int getResultType() {
        return this.dataType;
    }

    @Override
    public AbstractData getResult() {
        if (this.isNull) {
            return DataTypes.getNullValue(this.dataType);
        }
        return this.result;
    }

    @Override
    public void reset() {
        this.result = DataTypes.getNullValue(this.dataType);
        this.isNull = true;
    }

    @Override
    public void statistic(AbstractData value) {
        try {
            if (value == null || value.getAsNull()) {
                return;
            }
            if (this.result.compareTo(value) < 0) {
                this.result = value;
            }
            this.isNull = false;
        }
        catch (DataTypeException dataTypeException) {
            // empty catch block
        }
    }
}

