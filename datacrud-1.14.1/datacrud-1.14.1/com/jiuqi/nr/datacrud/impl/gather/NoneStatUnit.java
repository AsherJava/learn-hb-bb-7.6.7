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

public class NoneStatUnit
implements StatUnit {
    public int getStatKind() {
        return 0;
    }

    public int getResultType() {
        return 6;
    }

    public AbstractData getResult() {
        return AbstractData.valueOf((String)"\u2014\u2014");
    }

    public void reset() {
    }

    public void statistic(AbstractData value) {
    }
}

