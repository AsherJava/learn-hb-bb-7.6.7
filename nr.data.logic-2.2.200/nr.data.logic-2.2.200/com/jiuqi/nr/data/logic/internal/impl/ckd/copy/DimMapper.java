/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.common.DimensionMapInfo
 *  com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter
 */
package com.jiuqi.nr.data.logic.internal.impl.ckd.copy;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.common.DimensionMapInfo;
import com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter;
import java.util.List;
import java.util.Map;

public class DimMapper
implements DimensionMappingConverter {
    private final Map<DimensionValueSet, List<DimensionValueSet>> dimMap;

    public DimMapper(Map<DimensionValueSet, List<DimensionValueSet>> dimMap) {
        this.dimMap = dimMap;
    }

    public List<DimensionValueSet> getMappingMasterKey(DimensionValueSet curMasterKey) {
        return this.dimMap.get(curMasterKey);
    }

    public List<DimensionMapInfo> getMappingMasterKeys(List<DimensionValueSet> curMasterKeys) {
        throw new UnsupportedOperationException();
    }
}

