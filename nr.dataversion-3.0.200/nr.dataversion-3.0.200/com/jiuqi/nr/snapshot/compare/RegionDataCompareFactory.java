/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.io.params.base.RegionData
 */
package com.jiuqi.nr.snapshot.compare;

import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.snapshot.compare.AbstractCompareStrategy;
import com.jiuqi.nr.snapshot.compare.FixCompareStrategy;
import com.jiuqi.nr.snapshot.compare.FloatCompareStrategy;
import com.jiuqi.nr.snapshot.compare.FloatUniqueKeyCompareStrategy;

public class RegionDataCompareFactory {
    public AbstractCompareStrategy getRegionDataVersionCompareStrategy(RegionData region, boolean isDuplicate) {
        AbstractCompareStrategy regionDataVersionCompareStrategy = null;
        regionDataVersionCompareStrategy = region.getType() == DataRegionKind.DATA_REGION_SIMPLE.getValue() ? new FixCompareStrategy() : (!region.getAllowDuplicateKey() ? new FloatUniqueKeyCompareStrategy() : (region.getAllowDuplicateKey() && isDuplicate ? new FloatUniqueKeyCompareStrategy() : new FloatCompareStrategy()));
        return regionDataVersionCompareStrategy;
    }
}

