/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.snapshot;

import com.jiuqi.nr.snapshot.conts.Consts;

public interface IRegionCompareDifference {
    public String getRegionKey();

    public String getRegionName();

    public Consts.RegionCompareType getRegionCompareType();
}

