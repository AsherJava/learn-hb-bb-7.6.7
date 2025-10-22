/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.datastatus.facade.obj.ICopySetting
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.datastatus.facade.obj.ICopySetting;
import java.util.List;

public class CopyStatusInfo
implements ICopySetting {
    private final String formSchemeKey;
    private final List<String> formKeys;
    private final DimensionCollection copyDims;
    private final String srcPeriod;
    private final String srcAdjust;

    public CopyStatusInfo(String formSchemeKey, List<String> formKeys, DimensionCollection copyDims, String srcPeriod, String srcAdjust) {
        this.formSchemeKey = formSchemeKey;
        this.formKeys = formKeys;
        this.copyDims = copyDims;
        this.srcPeriod = srcPeriod;
        this.srcAdjust = srcAdjust;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public DimensionCollection getTargetDimension() {
        return this.copyDims;
    }

    public DimensionCombination getSourceDimension(DimensionCombination targetDimension) {
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder();
        for (FixedDimensionValue fixedDimensionValue : targetDimension) {
            if ("ADJUST".equals(fixedDimensionValue.getName())) {
                dimensionCombinationBuilder.setValue(fixedDimensionValue.getName(), fixedDimensionValue.getEntityID(), (Object)this.srcAdjust);
                continue;
            }
            if ("DATATIME".equals(fixedDimensionValue.getName())) {
                dimensionCombinationBuilder.setValue(fixedDimensionValue.getName(), fixedDimensionValue.getEntityID(), (Object)this.srcPeriod);
                continue;
            }
            dimensionCombinationBuilder.setValue(fixedDimensionValue);
        }
        return dimensionCombinationBuilder.getCombination();
    }
}

