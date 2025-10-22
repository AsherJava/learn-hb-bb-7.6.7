/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.common.DataRegion;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.executors.ExprExecRegion;

public interface ExprExecRegionCreator {
    public ExprExecRegion createExecRegion(DimensionSet var1, DataRegion var2);
}

