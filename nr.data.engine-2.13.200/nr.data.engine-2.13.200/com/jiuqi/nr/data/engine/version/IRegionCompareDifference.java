/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.version;

import com.jiuqi.nr.data.engine.util.Consts;

public interface IRegionCompareDifference {
    public String getRegionKey();

    public String getRegionName();

    public Consts.RegionCompareType getRegionCompareType();
}

