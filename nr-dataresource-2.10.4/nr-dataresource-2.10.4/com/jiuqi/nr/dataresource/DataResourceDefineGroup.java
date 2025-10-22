/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.GroupSetter
 *  com.jiuqi.nr.datascheme.api.core.Grouped
 *  com.jiuqi.nr.datascheme.api.core.OrderSetter
 */
package com.jiuqi.nr.dataresource;

import com.jiuqi.nr.dataresource.DataBasic;
import com.jiuqi.nr.datascheme.api.core.GroupSetter;
import com.jiuqi.nr.datascheme.api.core.Grouped;
import com.jiuqi.nr.datascheme.api.core.OrderSetter;
import java.time.Instant;

public interface DataResourceDefineGroup
extends DataBasic,
OrderSetter,
Grouped,
GroupSetter {
    @Override
    public String getKey();

    @Override
    public String getTitle();

    @Override
    public String getDesc();

    @Override
    public Instant getUpdateTime();
}

