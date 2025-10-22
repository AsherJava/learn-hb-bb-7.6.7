/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.executors.StatUnit;

class FirstStatUnit
implements StatUnit {
    private int dataType;
    private AbstractData result = null;
    private boolean isNull = true;

    public FirstStatUnit(int dataType) {
        this.dataType = dataType;
        this.result = DataTypes.getNullValue(dataType);
    }

    @Override
    public int getStatKind() {
        return 6;
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
        if (this.result.isNull) {
            this.result = value;
        }
        this.isNull = false;
    }
}

