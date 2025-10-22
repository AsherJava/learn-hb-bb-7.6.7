/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.snapshot.consts.RegionType
 *  com.jiuqi.nr.snapshot.message.DifferenceDataItem
 */
package com.jiuqi.nr.dataSnapshot.param;

import com.jiuqi.nr.dataSnapshot.param.IDataRegionCompareDifference;
import com.jiuqi.nr.snapshot.consts.RegionType;
import com.jiuqi.nr.snapshot.message.DifferenceDataItem;
import java.util.List;

public class FixedDataRegionCompareDifference
implements IDataRegionCompareDifference {
    private String regionName;
    private String regionKey;
    private List<DifferenceDataItem> differenceDataItems;
    private RegionType regionType;

    @Override
    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public List<DifferenceDataItem> getDifferenceDataItems() {
        return this.differenceDataItems;
    }

    public void setDifferenceDataItems(List<DifferenceDataItem> differenceDataItems) {
        this.differenceDataItems = differenceDataItems;
    }

    @Override
    public RegionType getRegionType() {
        return this.regionType;
    }

    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
    }
}

