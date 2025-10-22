/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.QueryEnvironment
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.nr.data.engine.gather.IDataGather;

public interface IDataGatherFactory {
    public IDataGather getDataGather(QueryEnvironment var1);
}

