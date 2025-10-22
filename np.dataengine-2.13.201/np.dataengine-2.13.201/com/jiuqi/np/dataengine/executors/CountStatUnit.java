/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.data.IntData;
import com.jiuqi.np.dataengine.executors.StatUnit;

class CountStatUnit
implements StatUnit {
    private int count = 0;
    private AbstractData result = null;
    private boolean isNull = true;

    CountStatUnit() {
    }

    @Override
    public int getStatKind() {
        return 2;
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
            this.result = new IntData(this.count);
        }
        return this.result;
    }

    @Override
    public void reset() {
        this.count = 0;
        this.result = null;
        this.isNull = true;
    }

    @Override
    public void statistic(AbstractData value) {
        if (!value.isNull) {
            ++this.count;
            this.result = null;
        }
        this.isNull = false;
    }
}

