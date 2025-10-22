/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.DataTypes
 *  com.jiuqi.np.dataengine.data.StringData
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.nr.data.engine.gather.param;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.data.StringData;
import com.jiuqi.np.dataengine.executors.StatUnit;

public class CountStringStatUnit
implements StatUnit {
    public static final int STAT_KIND_STRING_COUNT = 249;
    private int count = 0;
    private AbstractData result = null;
    private boolean isNull = true;

    public int getStatKind() {
        return 249;
    }

    public int getResultType() {
        return 6;
    }

    public AbstractData getResult() {
        if (this.isNull) {
            return DataTypes.getNullValue((int)this.getResultType());
        }
        if (this.result == null) {
            this.result = new StringData(String.valueOf(this.count), false);
        }
        return this.result;
    }

    public void reset() {
        this.count = 0;
        this.result = null;
        this.isNull = true;
    }

    public void statistic(AbstractData value) {
        if (!value.isNull) {
            ++this.count;
            this.result = null;
        }
        this.isNull = false;
    }
}

