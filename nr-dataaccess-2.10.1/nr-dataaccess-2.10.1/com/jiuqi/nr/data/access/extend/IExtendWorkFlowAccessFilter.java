/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.access.extend;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;
import java.util.Map;

public interface IExtendWorkFlowAccessFilter<T> {
    public String name();

    public T access(String var1, DimensionValueSet var2, String var3);

    public Map<String, T> batchAccess(String var1, DimensionCollection var2, List<String> var3);
}

