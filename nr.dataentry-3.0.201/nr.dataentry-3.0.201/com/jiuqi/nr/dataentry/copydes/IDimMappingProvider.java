/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.dataentry.copydes;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public interface IDimMappingProvider {
    public DimensionCollection getQuerySrcDim();

    public List<DimensionCombination> getTargetMasterKey(DimensionCombination var1);
}

