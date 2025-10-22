/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.message;

import com.jiuqi.nr.snapshot.consts.RegionType;
import com.jiuqi.nr.snapshot.message.DifferenceDataItem;
import com.jiuqi.nr.snapshot.service.IDataRegionCompareDifference;
import java.io.Serializable;
import java.util.List;

public class FixedDataRegionCompareDifference
implements IDataRegionCompareDifference,
Serializable {
    private static final long serialVersionUID = 627244314360243053L;
    private String regionName;
    private String regionKey;
    private List<DifferenceDataItem> differenceDataItems;
    private int differenceCount;
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
    public int getDifferenceCount() {
        return this.differenceCount;
    }

    public void setDifferenceCount(int differenceCount) {
        this.differenceCount = differenceCount;
    }

    @Override
    public RegionType getRegionType() {
        return this.regionType;
    }

    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
    }
}

