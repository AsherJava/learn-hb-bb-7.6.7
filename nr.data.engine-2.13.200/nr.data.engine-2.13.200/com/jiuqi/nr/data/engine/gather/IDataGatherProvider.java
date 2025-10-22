/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.QueryEnvironment
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.nr.data.engine.gather.IDataGather;
import com.jiuqi.nr.data.engine.gather.IDataGatherFactory;

public interface IDataGatherProvider {
    public IDataGather newDataGather();

    public IDataGather newDataGather(QueryEnvironment var1);

    public void registerDataGather(IDataGatherFactory var1);
}

