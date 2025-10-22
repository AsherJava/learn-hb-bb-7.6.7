/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.CurrencyData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatUnit;
import java.math.BigDecimal;

class CurrencyAvgUnit
implements StatUnit {
    private int count;
    private BigDecimal total;
    private AbstractData result = null;
    private boolean isNull = true;

    CurrencyAvgUnit() {
    }

    @Override
    public int getStatKind() {
        return 3;
    }

    @Override
    public int getResultType() {
        return 10;
    }

    @Override
    public AbstractData getResult() {
        if (this.isNull) {
            return DataTypes.getNullValue(this.getResultType());
        }
        if (this.result == null) {
            this.result = this.count > 0 ? new CurrencyData(this.total.divide(new BigDecimal(this.count), 4)) : CurrencyData.NULL;
        }
        return this.result;
    }

    @Override
    public void reset() {
        this.count = 0;
        this.total = new BigDecimal(0);
        this.result = null;
        this.isNull = true;
    }

    @Override
    public void statistic(AbstractData value) {
        try {
            if (!value.isNull) {
                ++this.count;
                this.total = this.total == null ? value.getAsCurrency() : this.total.add(value.getAsCurrency());
                this.result = null;
            }
            this.isNull = false;
        }
        catch (DataTypeException dataTypeException) {
            // empty catch block
        }
    }
}

