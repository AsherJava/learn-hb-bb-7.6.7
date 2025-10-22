/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.snapshot.consts.RegionType
 */
package com.jiuqi.nr.dataSnapshot.param;

import com.jiuqi.nr.dataSnapshot.param.FloatCompareDifference;
import com.jiuqi.nr.dataSnapshot.param.IDataRegionCompareDifference;
import com.jiuqi.nr.snapshot.consts.RegionType;
import java.util.List;

public class FloatDataRegionCompareDifference
implements IDataRegionCompareDifference {
    private String regionName;
    private String regionKey;
    private List<String> naturalKeys;
    private List<FloatCompareDifference> floatCompareDifferences;
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

    public List<String> getNaturalKeys() {
        return this.naturalKeys;
    }

    public void setNaturalKeys(List<String> naturalKeys) {
        this.naturalKeys = naturalKeys;
    }

    public List<FloatCompareDifference> getFloatCompareDifferences() {
        return this.floatCompareDifferences;
    }

    public void setFloatCompareDifferences(List<FloatCompareDifference> floatCompareDifferences) {
        this.floatCompareDifferences = floatCompareDifferences;
    }

    @Override
    public RegionType getRegionType() {
        return this.regionType;
    }

    public void setRegionType(RegionType regionType) {
        this.regionType = regionType;
    }
}

