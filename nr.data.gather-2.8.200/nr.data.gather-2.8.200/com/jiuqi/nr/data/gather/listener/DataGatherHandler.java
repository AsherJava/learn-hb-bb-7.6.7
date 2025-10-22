/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.engine.gather.GatherEntityMap
 */
package com.jiuqi.nr.data.gather.listener;

import com.jiuqi.nr.data.engine.gather.GatherEntityMap;
import com.jiuqi.nr.data.gather.bean.GatherParam;

public interface DataGatherHandler {
    public void executeBefore(String var1, GatherParam var2, GatherEntityMap var3);

    public void executeAfter(String var1, GatherParam var2, GatherEntityMap var3);
}

