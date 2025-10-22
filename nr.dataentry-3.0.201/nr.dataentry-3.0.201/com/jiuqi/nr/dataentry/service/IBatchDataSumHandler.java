/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.engine.gather.GatherEntityMap
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.data.engine.gather.GatherEntityMap;
import com.jiuqi.nr.dataentry.paramInfo.BatchDataSumInfo;

public interface IBatchDataSumHandler {
    public void afterBatchDataSum(String var1, BatchDataSumInfo var2, GatherEntityMap var3);
}

