/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.copydes.IDimMappingProvider;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CopyErrDesBTWEntityDimMappingProvider
implements IDimMappingProvider {
    private DimensionCollection sourceDim;
    private List<Map<String, DimensionValue>> targetDimList;
    private Map<String, Integer> sourceToTarget;
    private List<String> needCompareDimNames;

    public void setDims(DimensionCollection sourceDim, List<Map<String, DimensionValue>> sourceDimList, List<Map<String, DimensionValue>> targetDimList, List<String> needCompareDimNames) {
        this.sourceDim = sourceDim;
        this.targetDimList = targetDimList;
        this.needCompareDimNames = needCompareDimNames;
        this.sourceToTarget = new HashMap<String, Integer>();
        for (int i = 0; i < sourceDimList.size(); ++i) {
            Map<String, DimensionValue> source = sourceDimList.get(i);
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < this.needCompareDimNames.size(); ++j) {
                sb.append(source.get(this.needCompareDimNames.get(j)).getValue()).append("_");
            }
            this.sourceToTarget.put(sb.toString(), i);
        }
    }

    @Override
    public DimensionCollection getQuerySrcDim() {
        return this.sourceDim;
    }

    @Override
    public List<DimensionCombination> getTargetMasterKey(DimensionCombination srcMasterKey) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.needCompareDimNames.size(); ++i) {
            sb.append(srcMasterKey.getValue(this.needCompareDimNames.get(i))).append("_");
        }
        Map<String, DimensionValue> target = this.targetDimList.get(this.sourceToTarget.get(sb.toString()));
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (Map.Entry<String, DimensionValue> entry : target.entrySet()) {
            builder.setValue(entry.getKey(), new Object[]{entry.getValue().getValue()});
        }
        return builder.getCollection().getDimensionCombinations();
    }
}

