/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public interface IBatchZBAccessResult {
    public IAccessResult getAccess(DimensionCombination var1, String var2) throws Exception;
}

