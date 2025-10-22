/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.snapshot.consts.RegionType
 */
package com.jiuqi.nr.dataSnapshot.param;

import com.jiuqi.nr.snapshot.consts.RegionType;

public interface IDataRegionCompareDifference {
    public String getRegionKey();

    public String getRegionName();

    public RegionType getRegionType();
}

