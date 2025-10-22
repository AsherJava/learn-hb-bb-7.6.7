/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;
import java.util.Map;

public interface IRelationDimensionService {
    public List<DimensionValueSet> getRelationDimensionValueList(String var1, Map<String, List<String>> var2);
}

