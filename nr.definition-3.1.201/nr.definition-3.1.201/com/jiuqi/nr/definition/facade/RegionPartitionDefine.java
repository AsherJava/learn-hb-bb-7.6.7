/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.common.RegionPartitionType;

public interface RegionPartitionDefine
extends IMetaItem {
    public String getRegionKey();

    public RegionPartitionType getRegionPartitionType();

    public String getItemKeyString();

    public String[] getItemKeyArray();
}

