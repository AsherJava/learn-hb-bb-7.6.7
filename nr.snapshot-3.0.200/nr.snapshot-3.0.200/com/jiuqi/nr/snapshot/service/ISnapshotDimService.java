/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.snapshot.service;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.snapshot.output.QueryDimResult;

public interface ISnapshotDimService {
    public QueryDimResult queryDims(DimensionCollection var1, String var2);
}

