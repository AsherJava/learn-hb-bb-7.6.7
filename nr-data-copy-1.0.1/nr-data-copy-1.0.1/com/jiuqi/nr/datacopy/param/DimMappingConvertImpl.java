/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.dataservice.core.common.DimensionMapInfo
 *  com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter
 */
package com.jiuqi.nr.datacopy.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.common.DimensionMapInfo;
import com.jiuqi.nr.dataservice.core.common.DimensionMappingConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DimMappingConvertImpl
implements DimensionMappingConverter {
    private Map<DimensionValueSet, DimensionValueSet> source2TargetDVMap;

    public DimMappingConvertImpl(Map<DimensionValueSet, DimensionValueSet> source2TargetDVMap) {
        this.source2TargetDVMap = source2TargetDVMap;
    }

    public List<DimensionValueSet> getMappingMasterKey(DimensionValueSet curMasterKey) {
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        DimensionValueSet dimensionValueSet = this.source2TargetDVMap.get(curMasterKey);
        if (dimensionValueSet != null) {
            dimensionValueSets.add(dimensionValueSet);
        }
        return dimensionValueSets;
    }

    public List<DimensionMapInfo> getMappingMasterKeys(List<DimensionValueSet> curMasterKeys) {
        ArrayList<DimensionMapInfo> dimensionMapInfos = new ArrayList<DimensionMapInfo>();
        for (DimensionValueSet dimensionValue : curMasterKeys) {
            DimensionValueSet dimensionValueSet = this.source2TargetDVMap.get(dimensionValue);
            if (dimensionValueSet == null) continue;
            DimensionMapInfo dimensionMapInfo = new DimensionMapInfo();
            dimensionMapInfo.setSource(dimensionValue);
            dimensionMapInfo.setTarget(dimensionValueSet);
            dimensionMapInfos.add(dimensionMapInfo);
        }
        return dimensionMapInfos;
    }
}

