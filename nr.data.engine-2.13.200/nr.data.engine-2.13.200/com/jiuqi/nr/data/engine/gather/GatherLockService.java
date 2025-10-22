/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;

public interface GatherLockService {
    public boolean lockDataTable(List<DimensionValueSet> var1, String var2);

    public void unlockDataTable(List<DimensionValueSet> var1, String var2);
}

