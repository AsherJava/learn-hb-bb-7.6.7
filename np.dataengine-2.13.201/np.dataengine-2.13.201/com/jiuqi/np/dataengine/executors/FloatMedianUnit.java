/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.data.FloatData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.executors.StatUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FloatMedianUnit
implements StatUnit {
    private static final Logger logger = LoggerFactory.getLogger(FloatMedianUnit.class);
    private List<Double> list = new ArrayList<Double>();
    private AbstractData result = null;
    private boolean isNull = true;

    FloatMedianUnit() {
    }

    @Override
    public int getStatKind() {
        return 10;
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
            this.result = FloatData.valueOf(this.getMedian());
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
            this.list.add(value.getAsFloat());
            this.result = FloatData.valueOf(this.getMedian());
            this.isNull = false;
        }
        catch (DataTypeException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public double getMedian() {
        Collections.sort(this.list);
        int n = this.list.size();
        if (n > 0) {
            if (n % 2 == 0) {
                return (this.list.get(n / 2 - 1) + this.list.get(n / 2)) / 2.0;
            }
            return this.list.get((n + 1) / 2 - 1);
        }
        return 0.0;
    }
}

