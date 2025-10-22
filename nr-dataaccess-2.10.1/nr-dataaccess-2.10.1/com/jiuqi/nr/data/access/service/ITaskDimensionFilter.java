/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.service;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public interface ITaskDimensionFilter {
    public boolean exist(String var1, DimensionCombination var2);

    public List<DimensionCombination> filter(String var1, DimensionCollection var2);

    public DimensionCollection replace(String var1, DimensionCollection var2);
}

