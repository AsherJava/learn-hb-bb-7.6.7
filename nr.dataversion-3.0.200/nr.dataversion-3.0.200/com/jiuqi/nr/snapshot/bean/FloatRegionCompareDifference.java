/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.bean;

import com.jiuqi.nr.snapshot.IRegionCompareDifference;
import com.jiuqi.nr.snapshot.conts.Consts;

public class FloatRegionCompareDifference
implements IRegionCompareDifference {
    private String regionName;
    private String regionKey;
    private int initialRows;
    private int compareRows;
    private int differenceRows;
    private Consts.RegionCompareType regionCompareType = Consts.RegionCompareType.FLOAT;

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

    public int getInitialRows() {
        return this.initialRows;
    }

    public void setInitialRows(int initialRows) {
        this.initialRows = initialRows;
    }

    public int getCompareRows() {
        return this.compareRows;
    }

    public void setCompareRows(int compareRows) {
        this.compareRows = compareRows;
    }

    public int getDifferenceRows() {
        return this.differenceRows;
    }

    public void setDifferenceRows(int differenceRows) {
        this.differenceRows = differenceRows;
    }

    @Override
    public Consts.RegionCompareType getRegionCompareType() {
        return this.regionCompareType;
    }

    public void setRegionCompareType(Consts.RegionCompareType regionCompareType) {
        this.regionCompareType = regionCompareType;
    }
}

