/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.access.api;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public interface IDelTableDataService {
    public boolean deleteDataByRowKey(String var1, DimensionValueSet var2);

    public void truncateTable(String var1);

    public void clearTableData(String var1);
}

