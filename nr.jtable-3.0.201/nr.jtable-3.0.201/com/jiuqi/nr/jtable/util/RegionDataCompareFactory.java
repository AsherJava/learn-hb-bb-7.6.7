/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataRegionKind
 */
package com.jiuqi.nr.jtable.util;

import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.jtable.dataversion.compare.AbstractCompareStrategy;
import com.jiuqi.nr.jtable.dataversion.compare.FixCompareStrategy;
import com.jiuqi.nr.jtable.dataversion.compare.FloatCompareStrategy;
import com.jiuqi.nr.jtable.dataversion.compare.FloatUniqueKeyCompareStrategy;
import com.jiuqi.nr.jtable.params.base.RegionData;

public class RegionDataCompareFactory {
    public AbstractCompareStrategy getRegionDataVersionCompareStrategy(RegionData region) {
        AbstractCompareStrategy regionDataVersionCompareStrategy = null;
        regionDataVersionCompareStrategy = region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() ? new FixCompareStrategy() : (!region.getAllowDuplicateKey() ? new FloatUniqueKeyCompareStrategy() : new FloatCompareStrategy());
        return regionDataVersionCompareStrategy;
    }
}

