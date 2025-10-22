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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CurrencyMedianUnit
implements StatUnit {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyMedianUnit.class);
    private List<BigDecimal> list = new ArrayList<BigDecimal>();
    private AbstractData result = null;
    private boolean isNull = true;

    CurrencyMedianUnit() {
    }

    @Override
    public int getStatKind() {
        return 10;
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
            this.result = new CurrencyData(this.getMedian());
        }
        return this.result;
    }

    @Override
    public void reset() {
        this.list.clear();
        this.result = null;
        this.isNull = true;
    }

    @Override
    public void statistic(AbstractData value) {
        try {
            this.list.add(value.getAsCurrency());
            this.result = new CurrencyData(this.getMedian());
            this.isNull = false;
        }
        catch (DataTypeException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public BigDecimal getMedian() {
        Collections.sort(this.list);
        int n = this.list.size();
        if (n > 0) {
            if (n % 2 == 0) {
                return this.list.get(n / 2 - 1).add(this.list.get(n / 2)).divide(new BigDecimal(2), 4);
            }
            return this.list.get((n + 1) / 2 - 1);
        }
        return new BigDecimal(0);
    }
}

