/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.StatUnit;

class ConstantUnit
implements StatUnit {
    private int dataType;
    private AbstractData value;

    public void setValue(AbstractData value) {
        this.value = value;
    }

    public ConstantUnit(int dataType) {
        this.dataType = dataType;
    }

    public ConstantUnit(int dataType, AbstractData value) {
        this.dataType = dataType;
        this.value = value;
    }

    @Override
    public int getStatKind() {
        return 11;
    }

    @Override
    public int getResultType() {
        return this.dataType;
    }

    @Override
    public AbstractData getResult() {
        return this.value;
    }

    @Override
    public void reset() {
        this.value = null;
    }

    @Override
    public void statistic(AbstractData value) {
    }
}

