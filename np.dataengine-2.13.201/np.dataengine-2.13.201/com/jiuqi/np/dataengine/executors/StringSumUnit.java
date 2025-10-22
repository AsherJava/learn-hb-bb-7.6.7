/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.executors.StatUnit;

class StringSumUnit
implements StatUnit {
    private StringBuffer total = new StringBuffer(255);
    private AbstractData result = StringData.NULL;
    private boolean isNull = true;

    StringSumUnit() {
    }

    @Override
    public int getStatKind() {
        return 1;
    }

    @Override
    public int getResultType() {
        return 6;
    }

    @Override
    public AbstractData getResult() {
        if (this.isNull) {
            return DataTypes.getNullValue(this.getResultType());
        }
        if (this.result == null) {
            if (this.total.length() > 0) {
                this.total.setLength(this.total.length() - 1);
            }
            this.result = new StringData(this.total.toString());
        }
        return this.result;
    }

    @Override
    public void reset() {
        this.total.setLength(0);
        this.result = StringData.NULL;
        this.isNull = true;
    }

    @Override
    public void statistic(AbstractData value) {
        if (!value.isNull) {
            this.total.append(value.getAsString());
            this.total.append(';');
            this.result = null;
        }
        this.isNull = false;
    }
}

