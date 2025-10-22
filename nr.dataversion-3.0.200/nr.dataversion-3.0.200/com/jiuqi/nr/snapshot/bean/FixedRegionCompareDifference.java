/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.bean;

import com.jiuqi.nr.snapshot.IRegionCompareDifference;
import com.jiuqi.nr.snapshot.bean.CompareDifferenceItem;
import com.jiuqi.nr.snapshot.conts.Consts;
import java.util.List;

public class FixedRegionCompareDifference
implements IRegionCompareDifference {
    private String regionName;
    private String regionKey;
    private List<CompareDifferenceItem> updateItems;
    private Consts.RegionCompareType regionCompareType = Consts.RegionCompareType.FIXED;

    @Override
    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public List<CompareDifferenceItem> getUpdateItems() {
        return this.updateItems;
    }

    public void setUpdateItems(List<CompareDifferenceItem> updateItems) {
        this.updateItems = updateItems;
    }

    @Override
    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public Consts.RegionCompareType getRegionCompareType() {
        return this.regionCompareType;
    }

    public void setRegionCompareType(Consts.RegionCompareType regionCompareType) {
        this.regionCompareType = regionCompareType;
    }
}

