/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider
 */
package com.jiuqi.nr.data.gather.listener;

import com.jiuqi.nr.data.engine.gather.GatherEntityFilterProvider;
import com.jiuqi.nr.data.gather.bean.GatherParam;

public interface DataGatherUnitEntityFilterProvider
extends GatherEntityFilterProvider {
    public void setGatherParam(GatherParam var1);

    public void setMainDimensionName(String var1);
}

