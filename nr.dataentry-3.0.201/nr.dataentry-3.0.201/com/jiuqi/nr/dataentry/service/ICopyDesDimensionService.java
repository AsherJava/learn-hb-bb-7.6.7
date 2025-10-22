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

public interface ICopyDesDimensionService {
    public List<DimensionValueSet> getSourceDimensionValueList(String var1, String var2, Map<String, List<String>> var3);
}

