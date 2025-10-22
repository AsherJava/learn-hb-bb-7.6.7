/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.bean;

import com.jiuqi.nr.snapshot.IRegionCompareDifference;
import com.jiuqi.nr.snapshot.bean.NaturalKeyCompareDifference;
import com.jiuqi.nr.snapshot.conts.Consts;
import java.util.List;

public class FloatUniqueKeyRegionCompareDifference
implements IRegionCompareDifference {
    private String regionName;
    private String regionKey;
    private List<NaturalKeyCompareDifference> natures;
    private Consts.RegionCompareType regionCompareType = Consts.RegionCompareType.NATAURAL;

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

    public List<NaturalKeyCompareDifference> getNatures() {
        return this.natures;
    }

    public void setNatures(List<NaturalKeyCompareDifference> natures) {
        this.natures = natures;
    }

    @Override
    public Consts.RegionCompareType getRegionCompareType() {
        return this.regionCompareType;
    }

    public void setRegionCompareType(Consts.RegionCompareType regionCompareType) {
        this.regionCompareType = regionCompareType;
    }
}

