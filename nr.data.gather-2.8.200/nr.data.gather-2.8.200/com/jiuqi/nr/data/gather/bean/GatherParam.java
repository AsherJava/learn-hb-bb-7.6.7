/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.gather.bean;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.io.Serializable;
import java.util.Set;

public interface GatherParam
extends Serializable {
    public String getTaskKey();

    public String getFormSchemeKey();

    public DimensionCollection getDimensionCollection();

    public String getFormKeys();

    public Set<String> getIgnoreAccessItems();

    public GatherType getType();

    public static enum GatherType {
        DATA_GATHER,
        SELECT_GATHER,
        NODE_CHECK;

    }
}

