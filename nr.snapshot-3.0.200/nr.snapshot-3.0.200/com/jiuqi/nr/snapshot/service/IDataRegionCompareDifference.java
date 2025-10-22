/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot.service;

import com.jiuqi.nr.snapshot.consts.RegionType;

public interface IDataRegionCompareDifference {
    public String getRegionKey();

    public String getRegionName();

    public RegionType getRegionType();

    public int getDifferenceCount();
}

