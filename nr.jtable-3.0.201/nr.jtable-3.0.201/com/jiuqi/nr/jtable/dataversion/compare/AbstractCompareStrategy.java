/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.engine.version.IRegionCompareDifference
 */
package com.jiuqi.nr.jtable.dataversion.compare;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.engine.version.IRegionCompareDifference;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import com.jiuqi.nr.jtable.params.output.RegionDataSet;
import com.jiuqi.nr.jtable.util.RegionDataFactory;
import java.util.UUID;

public abstract class AbstractCompareStrategy {
    public abstract IRegionCompareDifference compareRegionVersionData(RegionData var1, JtableContext var2, UUID var3, UUID var4);

    public RegionDataSet getRegionDataSet(RegionData region, JtableContext jtableContext, UUID dataVersionId) {
        RegionQueryInfo regionQueryInfo = new RegionQueryInfo();
        JtableContext dataVersionJtableContext = new JtableContext(jtableContext);
        DimensionValue dataVersionDimension = new DimensionValue();
        dataVersionDimension.setName("VERSIONID");
        dataVersionDimension.setValue(dataVersionId.toString());
        dataVersionJtableContext.getDimensionSet().put("VERSIONID", dataVersionDimension);
        regionQueryInfo.setContext(dataVersionJtableContext);
        regionQueryInfo.setRegionKey(region.getKey());
        RegionDataFactory factory = new RegionDataFactory();
        factory.setIgnoreAnnoData(true);
        return factory.getRegionDataSet(region, regionQueryInfo);
    }
}

