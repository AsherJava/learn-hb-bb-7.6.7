/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.StatUnit
 */
package com.jiuqi.nr.datacrud.impl.gather;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.StatUnit;
import java.util.HashSet;
import java.util.Set;

public class DistinctCountStatUnit
implements StatUnit {
    private AbstractData result;
    private final Set<AbstractData> count = new HashSet<AbstractData>();

    public int getStatKind() {
        return 9;
    }

    public int getResultType() {
        return 4;
    }

    public AbstractData getResult() {
        return this.result;
    }

    public void reset() {
        this.count.clear();
        this.result = null;
    }

    public void statistic(AbstractData value) {
        if (value == null || value.isNull) {
            return;
        }
        this.count.add(value);
        this.result = AbstractData.valueOf((int)this.count.size());
    }
}

