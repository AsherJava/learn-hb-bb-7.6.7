/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.StatUnit;

class NoneStatUnit
implements StatUnit {
    private int dataType;

    public NoneStatUnit(int dataType) {
        this.dataType = dataType;
    }

    @Override
    public int getStatKind() {
        return 0;
    }

    @Override
    public int getResultType() {
        return this.dataType;
    }

    @Override
    public AbstractData getResult() {
        return AbstractData.valueOf(null, this.dataType);
    }

    @Override
    public void reset() {
    }

    @Override
    public void statistic(AbstractData value) {
    }
}

