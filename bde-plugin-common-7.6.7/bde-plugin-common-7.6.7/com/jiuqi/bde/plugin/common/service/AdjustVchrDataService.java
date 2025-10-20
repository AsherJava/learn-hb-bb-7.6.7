/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.FetchData
 *  com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition
 */
package com.jiuqi.bde.plugin.common.service;

import com.jiuqi.bde.bizmodel.execute.FetchData;
import com.jiuqi.bde.bizmodel.execute.intf.BalanceCondition;
import java.util.List;
import java.util.Map;

public interface AdjustVchrDataService {
    public FetchData getAdjustVchrFetchData(BalanceCondition var1);

    public FetchData getAdjustVchrXjllFetchData(BalanceCondition var1);

    public List<Object[]> reorderAdjustVchrList(Map<String, Integer> var1, FetchData var2);
}

