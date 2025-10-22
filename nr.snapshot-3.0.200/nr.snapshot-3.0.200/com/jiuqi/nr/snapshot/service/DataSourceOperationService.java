/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.snapshot.service;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.snapshot.output.ComparisonResult;
import com.jiuqi.nr.snapshot.service.DataSource;
import java.util.List;

public interface DataSourceOperationService {
    public List<ComparisonResult> comparison(DataSource var1, List<DataSource> var2, List<String> var3);

    public void reversion(String var1, DimensionCombination var2, DataSource var3, List<String> var4);
}

