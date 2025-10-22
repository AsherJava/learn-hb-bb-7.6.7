/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataservice.core.common;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.common.DimensionMapInfo;
import java.util.List;

public interface DimensionMappingConverter {
    public List<DimensionValueSet> getMappingMasterKey(DimensionValueSet var1);

    public List<DimensionMapInfo> getMappingMasterKeys(List<DimensionValueSet> var1);
}

